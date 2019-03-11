package org.atelier.geoquizzplayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1915857000934458526L;

	public BadRequest(String message) {
        super(message,null,false,false);
    }
    
    public BadRequest(String message, Throwable cause) {
        super(message, cause,false,false);
    }
    
    
}

