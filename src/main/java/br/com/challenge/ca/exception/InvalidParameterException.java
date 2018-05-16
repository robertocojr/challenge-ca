package br.com.challenge.ca.exception;

import br.com.challenge.ca.enumeration.ErrorEnum;

public class InvalidParameterException extends BaseException {

    public InvalidParameterException(final ErrorEnum errorEnum) {
        super(errorEnum);
    }
}
