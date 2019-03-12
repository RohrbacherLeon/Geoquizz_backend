package org.atelier.geoquizz_photos.boundaries;

import org.atelier.geoquizz_photos.entities.Photo;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/photos", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Photo.class)
public class PhotoRepresentation {

	private final PhotoResource pr;
	
	public PhotoRepresentation(PhotoResource pr) {
		this.pr = pr;
	}
	
}