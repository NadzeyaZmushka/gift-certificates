package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice("com.epam.esm.controller")
public class EntityExceptionHandler {

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionInfo handleNoSuchEntityException(NoSuchEntityException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        info.setErrorMessage(listErrors);
        info.setErrorCode(exception.getErrorCode());
        return info;
    }

    @ExceptionHandler(IncorrectDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionInfo handleIncorrectDataException(IncorrectDataException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        info.setErrorMessage(listErrors);
        info.setErrorCode(exception.getErrorCode());
        return info;
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionInfo handleDuplicateException(DuplicateException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        info.setErrorMessage(listErrors);
        info.setErrorCode(exception.getErrorCode());
        return info;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionInfo handleException() {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add("You entered incorrect data or parameters");
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return info;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionInfo handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add("Request body cannot be empty");
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return info;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionInfo handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorString = error.getDefaultMessage();
            listErrors.add(errorString);
        });
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return info;
    }

}
