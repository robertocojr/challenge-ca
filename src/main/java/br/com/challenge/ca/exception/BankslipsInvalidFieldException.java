package br.com.challenge.ca.exception;

import br.com.challenge.ca.enumeration.ErrorEnum;

public class BankslipsInvalidFieldException extends BaseException {

    public BankslipsInvalidFieldException() {
        super(ErrorEnum.BANKSLIPS_INVALID_FIELD);
    }
}
