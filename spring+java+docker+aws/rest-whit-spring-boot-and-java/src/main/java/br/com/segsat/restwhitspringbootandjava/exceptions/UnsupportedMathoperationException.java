package br.com.segsat.restwhitspringbootandjava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathoperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsupportedMathoperationException(String message) {
        super(message);
    }

    
}
