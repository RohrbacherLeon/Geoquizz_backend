package org.atelier.geoquizz_photos.boundaries;

import org.atelier.geoquizz_photos.entities.User;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(User.class)
public class UserRepresentation {

	private final UserResource ur;
	
	public UserRepresentation(UserResource ur) {
		this.ur = ur;
	}
	
}