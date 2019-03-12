package org.atelier.geoquizz_photos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {

	private static final long serialVersionUID = 123456789L;
	
	public BadRequest(String msg) {
		super("Requête incorrecte : " + msg, null, false, false);
	}
	
}