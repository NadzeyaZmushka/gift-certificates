package com.epam.esm.controller;

import com.epam.esm.exception.NoSuchTagException;
import com.epam.esm.exception.ExceptionInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.epam.esm.controller")
public class TagExceptionHandler {

    //todo: общий эксепшн
    @ExceptionHandler(NoSuchTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionInfoDTO handleNoSuchTagException(NoSuchTagException exception) {
        ExceptionInfoDTO data = new ExceptionInfoDTO();
        data.setInfo(exception.getMessage());

        return data;
    }

}
