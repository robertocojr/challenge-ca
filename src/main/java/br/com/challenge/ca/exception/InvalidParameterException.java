package br.com.challenge.ca.exception;

import org.springframework.http.HttpStatus;

import javax.xml.ws.http.HTTPException;

public class InvalidParameterException extends HTTPException {

    public InvalidParameterException() {
        super(HttpStatus.ACCEPTED.value());
    }

}
