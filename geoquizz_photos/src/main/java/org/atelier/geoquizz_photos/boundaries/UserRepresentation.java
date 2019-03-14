package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.atelier.geoquizz_photos.entities.User;
import org.atelier.geoquizz_photos.exceptions.BadRequest;
import org.atelier.geoquizz_photos.exceptions.NotFound;
import org.atelier.geoquizz_photos.exceptions.Unauthorized;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

@RestController
@RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

	private final UserResource ur;
	
	public UserRepresentation(UserResource ur) {
		this.ur = ur;
	}
	
	private Resource<User> userToResource(User user, boolean collection) {
		Link selfLink = linkTo(UserRepresentation.class).slash(user.getId()).withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(UserRepresentation.class).withRel("collection");
			return new Resource<>(user, selfLink, collectionLink);
		} else {
			return new Resource<>(user, selfLink);
		}
	}
	
	private Resources<Resource<User>> usersToResource(Iterable<User> users){
		Link selfLink = linkTo(UserRepresentation.class).withSelfRel();
		List<Resource<User>> userResources = new ArrayList<Resource<User>>();
		users.forEach(user -> userResources.add(userToResource(user, true)));
		return new Resources<>(userResources, selfLink);
	}
	
	private String generateToken() {
		return Jwts.builder().setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "cmdSecret").compact();
	}
	
	@GetMapping("/{id}/photos")
	public ResponseEntity<?> getPhotosOfUser(@RequestHeader(value="x-token") String token, @PathVariable("id") String id){
		Optional<User> query = ur.findById(id);
		if(query.isPresent()) {
			User user = query.get();
			if(user.getToken().equals(token)) {
				return new ResponseEntity<>(user.getPhotos(), HttpStatus.OK);
			} else {
				throw new Unauthorized("Token fourni invalide.");
			}
		} else {
			throw new NotFound("/users/" + id);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postUser(@RequestBody User user){
		if(ur.findById(user.getId()).isPresent()) {
			throw new BadRequest("Le login choisi est déjà utilisé.");
		} else if(user.getPassword().length() < 8) {
			throw new BadRequest("Le mot de passe doit être d'une longueur de 8 caractères minimum.");
		} else {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			user.setToken(generateToken());
			ur.save(user);
			return new ResponseEntity<>(userToResource(user, false), HttpStatus.OK);
		}
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> tryLogin(@RequestBody User user, @PathVariable("id") String id){
		Optional<User> query = ur.findById(id);
		if(query.isPresent()) {
			User stored = query.get();
			if(BCrypt.checkpw(user.getPassword(), stored.getPassword())) {
				return new ResponseEntity<>(userToResource(stored, false), HttpStatus.OK);
			} else {
				throw new BadRequest("Les mots de passe ne correspondent pas.");
			}
		} else {
			throw new NotFound("/users/" + id + "; Aucun compte associé à ce login n'a pu être trouvé.");
		}
	}
	
	@PostMapping("/{id}/photos")
	public ResponseEntity<?> postPhotoForUser(@RequestBody User user, @PathVariable("id") String id, @RequestHeader(value="x-token") String token){
		Optional<User> query = ur.findById(id);
		if(query.isPresent()) {
			User stored = query.get();
			if(stored.getToken().equals(token)) {
				if(stored.getPhotos().addAll(user.getPhotos())) {
					return new ResponseEntity<>(userToResource(stored, false), HttpStatus.OK);
				} else {
					throw new BadRequest("Photos fournies incorrectes.");
				}
			} else {
				throw new Unauthorized("Token fourni invalide.");
			}
		} else {
			throw new NotFound("/users/" + id);
		}
	}

}