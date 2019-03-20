package org.atelier.geoquizz_photos.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizz_photos.entities.Serie;
import org.atelier.geoquizz_photos.entityMirror.SerieMirror;
import org.atelier.geoquizz_photos.exceptions.BadRequest;
import org.atelier.geoquizz_photos.exceptions.NotFound;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("API pour les opérations CRUD sur les series.")
@RestController
@RequestMapping(value="/series", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

	private final SerieResource sr;
	private final PhotoResource pr;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	public SerieRepresentation(SerieResource sr, PhotoResource pr) {
		this.sr = sr;
		this.pr = pr;
	}
	
	private static SerieMirror serieToMirror(Serie serie) {
		return new SerieMirror(serie.getId(), serie.getVille(), serie.getMap_long(), serie.getMap_lat());
	}
	
	public static Resource<SerieMirror> serieToResource(Serie serie, boolean collection){
		Link selfLink = linkTo(SerieRepresentation.class).slash(serie.getId()).withSelfRel();
		SerieMirror sm = serieToMirror(serie);
		if(collection) {
			Link collectionLink = linkTo(SerieRepresentation.class).withRel("collection");
			Link photosLink = linkTo(SerieRepresentation.class).slash(serie.getId()).slash("photos").withRel("photos");
			return new Resource<>(sm, selfLink, collectionLink, photosLink);
		} else {
			return new Resource<>(sm, selfLink);
		}
	}
	
	public static Resources<Resource<SerieMirror>> seriesToResource(Iterable<Serie> series){
		Link selfLink = linkTo(SerieRepresentation.class).withSelfRel();
		List<Resource<SerieMirror>> serieResources = new ArrayList<Resource<SerieMirror>>();
		series.forEach(serie -> serieResources.add(serieToResource(serie, true)));
		return new Resources<>(serieResources, selfLink);
	}
	
	private String generateToken() {
		return Jwts.builder().setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "cmdSecret").compact();
	}
	
	@ApiOperation("Retourne toutes les series")
	@GetMapping
	public ResponseEntity<?> getAllSeries() {
		HashSet<Serie> series = new HashSet();
		sr.findAll().forEach(serie -> {
			if(serie.getId() != PhotoRepresentation.AWAITING_DATA) {
				series.add(serie);
			}
		});
		return new ResponseEntity<>(seriesToResource(sr.findAll()), HttpStatus.OK);
	}
	
	@ApiOperation("Retourne la serie dont l'id est fournie")
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getSerie(@ApiParam("Id de la serie") @PathVariable("id") String id){
		Optional<Serie> serie = sr.findById(id);
		if(serie.isPresent()) {
			return new ResponseEntity<>(serieToResource(serie.get(), true), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id);
		}
	}
	
	@ApiOperation("Retourne toutes les photos de la serie")
	@GetMapping(value="/{id}/photos")
	public ResponseEntity<?> getAllPhotosOfSerie(@ApiParam("Id de la serie") @PathVariable("id") String id){
		Optional<Serie> serie = sr.findById(id);
		if(serie.isPresent()) {
			return new ResponseEntity<>(PhotoRepresentation.photosToResources(serie.get().getPhotos()), HttpStatus.OK);
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
	
	/* NE FONCTIONNE PAS
	@ApiOperation("Upload les images des photos fournies en paramètre sur le serveur.")
	@PostMapping("/{id}/photos")
	public ResponseEntity<?> postPhotosOfSerie(@ApiParam("Images à uploader") @RequestPart FilesContainer fc, @ApiParam("Id de la série") @PathVariable("id") String id){
		Optional<Serie> query = sr.findById(id);
		MultipartFile[] files = fc.getFiles();
		System.out.println("TESTING ==================> " + files.toString());
		if(query.isPresent()) {
			Serie serie = query.get();
			HashSet<Photo> photos = new HashSet<Photo>(serie.getPhotos());
			int i = 0;
			System.out.println("TESTING ==================> " + files.length);
			for(MultipartFile file : files) {
				System.out.println("TESTING ==================> " + i + files[i].toString());
				String filename = fileStorageService.storeFile(file);
				Photo photo = new Photo("", 0, 0, ("/images/" + filename));
				photo.setId(UUID.randomUUID().toString());
				photo.setToken(generateToken());
				photo.setSerie(sr.findById(PhotoRepresentation.AWAITING_DATA).get());
				photos.add(pr.save(photo));
			}
			serie.setPhotos(photos);
			sr.save(serie);
			return new ResponseEntity<>(serieToResource(serie, true), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id);
		}
	}
	
	@ApiOperation("Ajoute les informations des photos fournies dans le body à la serie courante.")
	@PutMapping("/{id}/photos")
	public ResponseEntity<?> putPhotosOfSerie(@RequestBody Photo[] photos, @ApiParam("Id de la serie") @PathVariable("id") String id){
		Optional<Serie> query = sr.findById(id);
		if(query.isPresent()) {
			Serie serie = query.get();
			HashSet<Photo> set = new HashSet<Photo>();
			for(Photo photo : photos) {
				Optional<Photo> queryPhoto = pr.findById(photo.getId());
				if(queryPhoto.isPresent()) {
					set.add(queryPhoto.get());
				}
			}
			serie.setPhotos(set);
			sr.save(serie);
			return new ResponseEntity<>(serieToResource(serie, true), HttpStatus.OK);
		} else {
			throw new NotFound("/series/" + id);
		}
	}*/
	
}