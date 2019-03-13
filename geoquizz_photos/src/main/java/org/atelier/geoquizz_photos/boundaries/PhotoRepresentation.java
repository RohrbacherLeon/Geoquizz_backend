package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.atelier.geoquizz_photos.entities.Photo;
import org.atelier.geoquizz_photos.entities.Serie;
import org.atelier.geoquizz_photos.exceptions.BadRequest;
import org.atelier.geoquizz_photos.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value="/photos", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Photo.class)
public class PhotoRepresentation {
	
	private final PhotoResource pr;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	public PhotoRepresentation(PhotoResource pr) {
		this.pr = pr;
	}
	
	public Resource<Photo> photoToResource(Photo photo, boolean collection){
		Link selfLink = linkTo(PhotoRepresentation.class).slash(photo.getId()).withSelfRel();
		if(collection) {
			//Link collectionLink = linkTo(PhotoRepresentation.class).withRel("collection");
			Link serieLink = linkTo(SerieRepresentation.class).slash(photo.getSerie().getId()).withRel("serie");
			return new Resource<>(photo, selfLink, serieLink);
		} else {
			return new Resource<>(photo, selfLink);
		}
	}
	
	public Resources<Resource<Photo>> photosToResources(Iterable<Photo> photos){
		Link selfLink = linkTo(PhotoRepresentation.class).withSelfRel();
		List<Resource<Photo>> photoResources = new ArrayList<Resource<Photo>>();
		photos.forEach(photo -> photoResources.add(photoToResource(photo, true)));
		return new Resources<>(photoResources, selfLink);
	}
	
	@PostMapping
	public ResponseEntity<?> postPhoto(@RequestBody Photo photo, @RequestParam("file") MultipartFile file){
		return new ResponseEntity<>(fileStorageService.storeFile(file), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> endUpload(@RequestBody Photo photo){
		photo.setId(UUID.randomUUID().toString());
		pr.save(photo);
		return new ResponseEntity<>(photoToResource(photo, false), HttpStatus.OK);
	}
	
}