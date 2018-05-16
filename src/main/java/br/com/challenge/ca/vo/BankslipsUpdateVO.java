package br.com.challenge.ca.vo;

import br.com.challenge.ca.enumeration.BankslipsStatusEnum;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class BankslipsUpdateVO implements Serializable {

    @NotBlank
    public BankslipsStatusEnum status;

    public BankslipsUpdateVO() {
    }

    public BankslipsUpdateVO(@NotBlank BankslipsStatusEnum status) {
        this.status = status;
    }
}
