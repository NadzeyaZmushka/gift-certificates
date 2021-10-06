package com.epam.esm.exception;

public class NoSuchEntityException extends RuntimeException {

    private final int errorCode;

    public NoSuchEntityException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
