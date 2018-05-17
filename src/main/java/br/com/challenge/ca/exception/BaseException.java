package br.com.challenge.ca.exception;

import br.com.challenge.ca.enumeration.ErrorEnum;

public class BaseException extends RuntimeException {

    private ErrorEnum error;

    public BaseException(ErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorEnum getError() {
        return error;
    }
}
