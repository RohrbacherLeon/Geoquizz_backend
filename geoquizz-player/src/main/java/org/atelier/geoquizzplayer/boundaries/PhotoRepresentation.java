package org.atelier.geoquizzplayer.boundaries;

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

@RestController
@RequestMapping(value="/photos")
@ExposesResourceFor(Photo.class)
public class PhotoRepresentation {

	private final PhotoRessource pr;
	
	
	public PhotoRepresentation(PhotoRessource pr) {
		this.pr = pr;
	}
	
	@GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws Exception {

    	ClassPathResource imgFile = new ClassPathResource("images/" + name);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
	
}