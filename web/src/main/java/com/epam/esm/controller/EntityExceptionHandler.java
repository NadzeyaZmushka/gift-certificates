package com.epam.esm.controller;

import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ExceptionInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.epam.esm.controller")
public class EntityExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionInfoDTO handleNoSuchTagException(NoSuchEntityException exception) {
        ExceptionInfoDTO data = new ExceptionInfoDTO();
        data.setInfo(exception.getMessage());

        return data;
    }

}
