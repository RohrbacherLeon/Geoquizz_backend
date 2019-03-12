package org.atelier.geoquizzplayer.boundaries;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.atelier.geoquizzplayer.EntityMirror.PartieMirroir;
import org.atelier.geoquizzplayer.EntityMirror.PartieMirroirWithToken;
import org.atelier.geoquizzplayer.entity.Partie;
import org.atelier.geoquizzplayer.exception.NotFound;
import org.atelier.geoquizzplayer.exception.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(value="/parties", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Partie.class)
public class PartieRepresentation {
	
	@Autowired
	private final PartieRessource pr;
	
	private String generateToken() {
   	 String jwtToken = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "cmdSecret")
                .compact();
   	 return jwtToken;
    }
	
	private PartieMirroir partieToMirror(Partie c, Boolean showToken) {
    	PartieMirroir cm = null;
    	
    	if(!showToken) {
    		cm = new PartieMirroir(c.getId(), c.getNb_photos(), c.getStatus(), c.getScore(), c.getJoueur(), c.getPhotos());
    	}else {
    		
    		if(showToken) {
        		cm = new PartieMirroirWithToken(c.getId(), c.getNb_photos(), c.getStatus(), c.getScore(), c.getJoueur(), c.getPhotos(), c.getToken());
        	}
    	}
	   	 
    	return cm;
    }
	
	private Resource<PartieMirroir> partieToResource(Partie commande, Boolean showToken, Boolean collection) {
   	 
        Link selfLink      = linkTo(PartieRepresentation.class).slash(commande.getId()).withSelfRel();
   	 	PartieMirroir cm = partieToMirror(commande, showToken);

        Link collectionLink = linkTo(PartieRepresentation.class).withRel("collection");
        return new Resource<>(cm, selfLink, collectionLink);
    }
	
	public PartieRepresentation(PartieRessource pr) {
		this.pr = pr;
	}
	
	@GetMapping
    public ResponseEntity<?> getAllParties() throws BadRequest {
		Iterable<Partie> allCategories = pr.findAll();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<?> postPartie(@RequestBody Partie partie) throws BadRequest {
		partie.setId(UUID.randomUUID().toString());
		
		String jwtToken = generateToken();
        partie.setToken(jwtToken);
        
        Partie newPartie = pr.save(partie);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(PartieRepresentation.class).slash(newPartie.getId()).toUri());
        return new ResponseEntity<>(newPartie,responseHeaders,HttpStatus.CREATED);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getPartieWithIdAndToken(@PathVariable("id") String id, @RequestHeader(value = "x-token") String token)throws BadRequest {
		
		if(token.isEmpty()) {
			throw new BadRequest("Token empty");
		}
		
		Optional<Partie> partie = pr.findByIdAndToken(id, token);
    	
    	if(partie.isPresent()) {
    		return new ResponseEntity<>(partieToResource(partie.get(), false, true) ,HttpStatus.OK);
    	}
    	
        throw new NotFound("Partie not found");
    }
	
	@GetMapping("/{id}/photos")
    public ResponseEntity<?> getPhotos(@PathVariable("id") String id)throws BadRequest {
		return Optional.ofNullable(pr.findById(id))
				.filter(Optional::isPresent)
				.map(partie -> new ResponseEntity<>(partie.get().getPhotos(), HttpStatus.OK))
				.orElseThrow(() -> new NotFound("Photos introuvables"));
    }
	
	@PutMapping("/{id}/end")
    public ResponseEntity<?> updatePartie(@RequestBody Partie partieUpdated, @PathVariable("id") String id, @RequestHeader(value = "x-token") String token)throws BadRequest {
		
		if(token.isEmpty()) {
			throw new BadRequest("Token empty");
		}
		
		Optional<Partie> partie = pr.findByIdAndToken(id, token);
		
		if(partie.isPresent()) {
    		Partie p = partie.get();
    		System.out.println(p.getStatus() != null);
    		System.out.println(p.getStatus());
    		
    		if(p.getStatus() != null) {
    			p.setStatus(partieUpdated.getStatus());
    			pr.save(p);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setLocation(linkTo(PartieRepresentation.class).slash(p.getId()).toUri());
                return new ResponseEntity<>(partieToResource(p, false, true), responseHeaders, HttpStatus.OK);
    		}else {
    			throw new BadRequest("Impossible de payer cette commande");
    		}
    		
    	}
    	
    	throw new NotFound("Commande inexistante");
    }
	
	@DeleteMapping(value="/{id}")
    public ResponseEntity<?> deletePartie(@PathVariable("id") String id, @RequestHeader(value = "x-token") String token) {
    	
    	Optional<Partie> partie = pr.findByIdAndToken(id, token);
    	
    	if(partie.isPresent()) {
    		Partie p = partie.get();
    		pr.delete(p);
            return new ResponseEntity<>(partieToResource(p, false, true), HttpStatus.OK);
    	}
    		
    	throw new NotFound("Partie inexistante");
    }

}
