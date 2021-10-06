package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.epam.esm.controller")
public class EntityExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionInfo handleNoSuchEntityException(NoSuchEntityException exception) {
        ExceptionInfo info = new ExceptionInfo();
        info.setErrorMessage(exception.getMessage());
        info.setErrorCode(exception.getErrorCode());
        return info;
    }

}
