package com.epam.esm.exception;

public class IncorrectDataException extends RuntimeException {

    private final int errorCode;

    public IncorrectDataException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
