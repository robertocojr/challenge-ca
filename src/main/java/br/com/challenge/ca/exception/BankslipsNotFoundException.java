package br.com.challenge.ca.exception;

import org.springframework.http.HttpStatus;

import javax.xml.ws.http.HTTPException;

public class BankslipsNotFoundException extends HTTPException {

    public BankslipsNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}
