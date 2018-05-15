package br.com.challenge.ca.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class BankslipsUpdateVO implements Serializable {

    @NotBlank
    public String status;

    public BankslipsUpdateVO() {
    }

    public BankslipsUpdateVO(@NotBlank String status) {
        this.status = status;
    }
}
