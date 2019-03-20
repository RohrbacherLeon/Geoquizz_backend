package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizz_photos.entities.User;
import org.atelier.geoquizz_photos.entityMirror.UserMirror;
import org.atelier.geoquizz_photos.entityMirror.UserMirrorWithToken;
import org.atelier.geoquizz_photos.exceptions.BadRequest;
import org.atelier.geoquizz_photos.exceptions.NotFound;
import org.atelier.geoquizz_photos.exceptions.Unauthorized;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("API pour les opérations CRUD sur les users.")
@RestController
@RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

	private final UserResource ur;
	
	public UserRepresentation(UserResource ur) {
		this.ur = ur;
	}
	
	private UserMirror userToMirror(User user, boolean token) {
		UserMirror um = null;
		if(!token) {
			um = new UserMirror(user.getId(), user.getLogin());
		}else {
			um = new UserMirrorWithToken(user.getId(), user.getLogin(), user.getToken());
		}
    	return um;
    }
	
	public Resource<UserMirror> userToResource(User user, boolean collection, boolean showToken) {
		Link selfLink = linkTo(UserRepresentation.class).slash(user.getId()).withSelfRel();
		UserMirror um = userToMirror(user, showToken);
		if(collection) {
			Link collectionLink = linkTo(UserRepresentation.class).withRel("collection");
			return new Resource<>(um, selfLink, collectionLink);
		} else {
			return new Resource<>(um, selfLink);
		}
	}
	
	private String generateToken() {
		return Jwts.builder().setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "cmdSecret").compact();
	}
	
	@ApiOperation("Retourne toutes les photos uploadées par le user dont l'id est fournie et qui ne sont pas encore assignées à une serie")
	@GetMapping("/{id}/photos")
	public ResponseEntity<?> getPhotosOfUser(@ApiParam("Token d'authentification du user") @RequestHeader(value="x-token") String token, @ApiParam("Id du user") @PathVariable("id") String id){
		Optional<User> query = ur.findById(id);
		if(query.isPresent()) {
			User user = query.get();
			if(user.getToken().equals(token)) {
				return new ResponseEntity<>(PhotoRepresentation.photosToResources(user.getPhotos()), HttpStatus.OK);
			} else {
				throw new Unauthorized("Token fourni invalide.");
			}
		} else {
			throw new NotFound("/users/" + id);
		}
	}
	
	@ApiOperation("Inscrit un user si les conditions sont remplies : login unique et mot de passe de 8 caractères minimum")
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody User user){
		if(ur.findByLogin(user.getLogin()).isPresent()) {
			throw new BadRequest("Le login choisi est déjà utilisé.");
		} else if(user.getPassword().length() < 8) {
			throw new BadRequest("Le mot de passe doit être d'une longueur de 8 caractères minimum.");
		} else {
			user.setId(UUID.randomUUID().toString());
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			ur.save(user);
			return new ResponseEntity<>(userToResource(user, false, false), HttpStatus.OK);
		}
	}
	
	@ApiOperation("Retourne le user avec son token si son login et mot de passe fournis sont correctes")
	@PostMapping("/signin")
	public ResponseEntity<?> tryLogin(@RequestBody User user){
		Optional<User> query = ur.findByLogin(user.getLogin());
		if(query.isPresent()) {
			User stored = query.get();
			stored.setToken(generateToken());
			ur.save(stored);
			if(BCrypt.checkpw(user.getPassword(), stored.getPassword())) {
				return new ResponseEntity<>(userToResource(stored, false, true), HttpStatus.OK);
			} else {
				throw new BadRequest("Les mots de passe ne correspondent pas.");
			}
		} else {
			throw new NotFound("Aucun compte associé à ce login n'a pu être trouvé.");
		}
	}
	
	/**
	 * Method used to generate a bcrypt hash and jwt token to seed the database
	 * @param user
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> createPasswordAndToken(@RequestBody User user){
		user.setLogin(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		user.setToken(generateToken());
		return new ResponseEntity<>(userToResource(user, false, true), HttpStatus.OK);
	}

}