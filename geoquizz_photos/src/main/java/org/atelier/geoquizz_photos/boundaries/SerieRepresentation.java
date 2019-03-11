package org.atelier.geoquizz_photos.boundaries;

import org.atelier.geoquizz_photos.entities.Serie;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/series", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

	private final SerieResource sr;
	
	public SerieRepresentation(SerieResource sr) {
		this.sr = sr;
	}
	
}