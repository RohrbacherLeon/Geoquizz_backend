package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizz_photos.entities.Serie;
import org.atelier.geoquizz_photos.exceptions.BadRequest;
import org.atelier.geoquizz_photos.exceptions.NotFound;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("API pour les opérations CRUD sur les series.")
@RestController
@RequestMapping(value="/series", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

	private final SerieResource sr;
	
	public SerieRepresentation(SerieResource sr) {
		this.sr = sr;
	}
	
	private Resource<Serie> serieToResource(Serie serie, boolean collection){
		Link selfLink = linkTo(SerieRepresentation.class).slash(serie.getId()).withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(SerieRepresentation.class).withRel("collection");
			Link photosLink = linkTo(SerieRepresentation.class).slash(serie.getId()).slash("photos").withRel("photos");
			return new Resource<>(serie, selfLink, collectionLink, photosLink);
		} else {
			return new Resource<>(serie, selfLink);
		}
	}
	
	private Resources<Resource<Serie>> seriesToResource(Iterable<Serie> series){
		Link selfLink = linkTo(SerieRepresentation.class).withSelfRel();
		List<Resource<Serie>> serieResources = new ArrayList<Resource<Serie>>();
		series.forEach(serie -> serieResources.add(serieToResource(serie, true)));
		return new Resources<>(serieResources, selfLink);
	}
	
	@ApiOperation("Retourne toutes les series")
	@GetMapping
	public ResponseEntity<?> getAllSeries() {
		return new ResponseEntity<>(seriesToResource(sr.findAll()), HttpStatus.OK);
	}
	
	@ApiOperation("Retourne la serie dont l'id est fournie")
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getSerie(@ApiParam("Id de la serie") @PathVariable("id") String id){
		Optional<Serie> serie = sr.findById(id);
		if(serie.isPresent()) {
			return new ResponseEntity<>(serieToResource(serie.get(), false), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id);
		}
	}
	
	@ApiOperation("Retourne toutes les photos de la serie")
	@GetMapping(value="/{id}/photos")
	public ResponseEntity<?> getAllPhotosOfSerie(@ApiParam("Id de la serie") @PathVariable("id") String id){
		Optional<Serie> serie = sr.findById(id);
		if(serie.isPresent()) {
			return new ResponseEntity<>(serie.get().getPhotos(), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id + "/photos");
		}
	}
	
	@ApiOperation("Créér une nouvelle serie à partir de celle fournie en body. Il ne peut y avoir qu'une serie par ville")
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