package org.atelier.geoquizzplayer.boundaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.atelier.geoquizzplayer.entity.Photo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/photos")
@ExposesResourceFor(Photo.class)
public class PhotoRepresentation {

	private final PhotoRessource pr;
	
	
	public PhotoRepresentation(PhotoRessource pr) {
		this.pr = pr;
	}
	
	@ApiOperation(value = "Récupèrer une image")
	@GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@ApiParam("Nom de l'image avec son extension") @PathVariable("name") String name) throws Exception {

        FileInputStream fis = new FileInputStream(new File("/images/"+name));

        byte[] bytes = StreamUtils.copyToByteArray(fis);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
	
}