package org.atelier.geoquizz_photos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException {

	private static final long serialVersionUID = 123456789L;
	
	public NotFound(String msg) {
		super("Ressource introuvable : " + msg, null, false, false);
	}
}
