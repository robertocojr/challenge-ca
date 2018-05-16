package br.com.challenge.ca.exception;

import br.com.challenge.ca.enumeration.ErrorEnum;

public class BankslipsNotFoundException extends BaseException {

    public BankslipsNotFoundException() {
        super(ErrorEnum.BANKSLIPS_NOT_FOUND);
    }
}
