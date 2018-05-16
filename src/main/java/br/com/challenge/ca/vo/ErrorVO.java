package br.com.challenge.ca.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorVO implements Serializable{

    public static final int DEFAULT_ERROR_CODE = 999;

    public static final String DEFAULT_ERROR_MESSAGE = "Generic error.";

    private int code = DEFAULT_ERROR_CODE;

    private String message = DEFAULT_ERROR_MESSAGE;

    public ErrorVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
