package org.atelier.geoquizz_photos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
public class Unauthorized extends RuntimeException {
	
	private static final long serialVersionUID = 123456789L;
	
	public Unauthorized(String msg) {
		super(msg, null, false, false);
	}

}
