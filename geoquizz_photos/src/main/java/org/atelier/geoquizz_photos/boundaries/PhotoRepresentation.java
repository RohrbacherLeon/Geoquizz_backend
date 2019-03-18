package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizz_photos.entities.Photo;
import org.atelier.geoquizz_photos.exceptions.NotFound;
import org.atelier.geoquizz_photos.exceptions.Unauthorized;
import org.atelier.geoquizz_photos.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("API pour les opérations CRUD sur les photos.")
@RestController
@RequestMapping(value="/photos", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Photo.class)
public class PhotoRepresentation {
	
	private final PhotoResource pr;
	private final SerieResource sr;
	public static final String AWAITING_DATA = "0";
	
	@Autowired
	private FileStorageService fileStorageService;
	
	public PhotoRepresentation(PhotoResource pr, SerieResource sr) {
		this.pr = pr;
		this.sr = sr;
	}
	
	public static Resource<Photo> photoToResource(Photo photo, boolean collection){
		Link selfLink = linkTo(PhotoRepresentation.class).slash(photo.getId()).withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(PhotoRepresentation.class).withRel("collection");
			Link serieLink = linkTo(SerieRepresentation.class).slash(photo.getSerie().getId()).withRel("serie");
			return new Resource<>(photo, selfLink, collectionLink, serieLink);
		} else {
			return new Resource<>(photo, selfLink);
		}
	}
	
	public static Resources<Resource<Photo>> photosToResources(Iterable<Photo> photos){
		Link selfLink = linkTo(PhotoRepresentation.class).withSelfRel();
		List<Resource<Photo>> photoResources = new ArrayList<Resource<Photo>>();
		photos.forEach(photo -> photoResources.add(photoToResource(photo, true)));
		return new Resources<>(photoResources, selfLink);
	}
	
	private String generateToken() {
		return Jwts.builder().setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "cmdSecret").compact();
	}
	
	@ApiOperation("Recupère les informations d'une photo dont l'id est fournie en paramètre.")
	@GetMapping("/{uri}")
	public ResponseEntity<?> getPhoto(@ApiParam("Id de la photo") @PathVariable("uri") String uri){
		Optional<Photo> query = pr.findByUrl(uri);
		if(query.isPresent()) {
			Photo photo = query.get();
			return new ResponseEntity<>(photoToResource(photo, false), HttpStatus.OK);
		} else {
			throw new NotFound("/photos/" + uri);
		}
	}
	
	
	@ApiOperation("Upload de l'image d'une photo sur le serveur")
	@PostMapping
	public ResponseEntity<?> postPhoto(@ApiParam("Image à uploader") @RequestPart("file") MultipartFile file){
		String filename = fileStorageService.storeFile(file);
		Photo photo = new Photo("", 0, 0, ("/images/" + filename));
		photo.setId(UUID.randomUUID().toString());
		photo.setToken(generateToken());
		photo.setSerie(sr.findById(AWAITING_DATA).get());
		pr.save(photo);
		return new ResponseEntity<>(photoToResource(photo, false), HttpStatus.OK);
	}
	
	@ApiOperation("Modification des données d'une photo")
	@PutMapping(value="/{id}")
	public ResponseEntity<?> putPhoto(@RequestBody Photo updated, @ApiParam("Token de la photo") @RequestHeader(value="x-token") String token, @ApiParam("Id de la photo") @PathVariable("id") String id){
		Optional<Photo> query = pr.findById(id);
		if(query.isPresent()) {
			Photo photo = query.get();
			if(photo.getToken().equals(token)) {
				photo.setDescription(updated.getDescription());
				photo.setLatitude(updated.getLatitude());
				photo.setLongitude(updated.getLongitude());
				photo.setSerie(updated.getSerie());
				photo.setUser(updated.getUser());
				pr.save(photo);
				return new ResponseEntity<>(photoToResource(photo, false), HttpStatus.OK);
			} else {
				throw new Unauthorized("Token fourni invalide.");
			}
		} else {
			throw new NotFound("/photos/" + id);
		}		
	}
	
}