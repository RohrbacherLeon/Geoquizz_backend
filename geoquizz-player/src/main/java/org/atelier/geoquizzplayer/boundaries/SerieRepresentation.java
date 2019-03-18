package org.atelier.geoquizzplayer.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizzplayer.EntityMirror.PartieMirroir;
import org.atelier.geoquizzplayer.EntityMirror.PartieMirroirWithToken;
import org.atelier.geoquizzplayer.EntityMirror.SerieMirroir;
import org.atelier.geoquizzplayer.entity.Partie;
import org.atelier.geoquizzplayer.entity.Photo;
import org.atelier.geoquizzplayer.entity.Serie;
import org.atelier.geoquizzplayer.exception.BadRequest;
import org.atelier.geoquizzplayer.exception.NotFound;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/series", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

	private final SerieResource sr;
	
	public SerieRepresentation(SerieResource sr) {
		this.sr = sr;
	}
	
	private SerieMirroir serieToMirror(Serie s) {
    	SerieMirroir cm = new SerieMirroir(s.getId(), s.getVille(), s.getMap_long(), s.getMap_lat());
    	
    	return cm;
    }
	
	private Resource<SerieMirroir> serieToResource(Serie serie, boolean collection){
		Link selfLink = linkTo(SerieRepresentation.class).slash(serie.getId()).withSelfRel();
		SerieMirroir sm = serieToMirror(serie);
		if(collection) {
			Link collectionLink = linkTo(SerieRepresentation.class).withRel("collection");
			Link photosLink = linkTo(SerieRepresentation.class).slash(serie.getId()).slash("photos").withRel("photos");
			return new Resource<>(sm, selfLink, collectionLink, photosLink);
		} else {
			return new Resource<>(sm, selfLink);
		}
	}
	
	private Resources<Resource<SerieMirroir>> seriesToResource(Iterable<Serie> series){
		Link selfLink = linkTo(SerieRepresentation.class).withSelfRel();
		List<Resource<SerieMirroir>> serieResources = new ArrayList<Resource<SerieMirroir>>();
		series.forEach(serie -> serieResources.add(serieToResource(serie, true)));
		return new Resources<>(serieResources, selfLink);
	}
	
	@ApiOperation(value = "Récupèrer toutes les series existantes")
	@GetMapping
	public ResponseEntity<?> getAllSeries() {
		return new ResponseEntity<>(seriesToResource(sr.findAll()), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Récupèrer une série")
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getSerie(@ApiParam("Id de la série") @PathVariable("id") String id){
		Optional<Serie> serie = sr.findById(id);
		if(serie.isPresent()) {
			return new ResponseEntity<>(serieToResource(serie.get(), false), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id);
		}
	}
	
	@ApiOperation(value = "Créer une série")
	@PostMapping
	public ResponseEntity<?> postSerie(@RequestBody Serie serie){
		serie.setId(UUID.randomUUID().toString());
		if(serie.getVille() != null) {
			if(!sr.findByVille(serie.getVille()).isPresent()) {
				sr.save(serie);
				return new ResponseEntity<>(serieToResource(serie, false), HttpStatus.CREATED);
			} else {
				throw new BadRequest("La ville fournie est déjà utilisée.");
			}
		} else {
			throw new BadRequest("La ville doit être spécifiée.");
		}	
	}
	
}