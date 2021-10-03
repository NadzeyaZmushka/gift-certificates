package com.epam.esm.controller;

import com.epam.esm.exception.NoSuchTagException;
import com.epam.esm.exception.TagIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TagExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<TagIncorrectData> handleException(NoSuchTagException exception) {
        TagIncorrectData data = new TagIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

}
