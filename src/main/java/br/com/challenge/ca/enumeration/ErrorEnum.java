package br.com.challenge.ca.enumeration;

public enum ErrorEnum {

    BANKSLIPS_NOT_FOUND(1001, "Bankslip not found with the specified id"),

    BANKSLIPS_INVALID_FIELD(1001, "Invalid bankslip provided.The possible reasons are:" +
            "A field of the provided bankslip was null or with invalid values"),

    BANKSLIPS_INVALID_UUID(1002, "Invalid id provided - it must be a valid UUID"),

    BANKSLIPS_INVALID_BODY(1003, "Bankslip not provided in the request body");

    private int code;

    private String message;

    ErrorEnum(int code, String message) {
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
