package com.epam.esm.exception;

import com.epam.esm.config.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice("com.epam.esm.controller")
@RequiredArgsConstructor
public class EntityExceptionHandler {

    private final Translator translator;

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
    public ExceptionInfo handleException(Exception exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
//        listErrors.add("You entered incorrect data or parameters");
        listErrors.add(exception.getMessage());
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return info;
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionInfo handleJwtAuthenticationException(JwtAuthenticationException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.FORBIDDEN.value());
        return info;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionInfo handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ExceptionInfo info = new ExceptionInfo();
        List<String> listErrors = new ArrayList<>();
        listErrors.add(translator.toLocale("message.empty.body"));
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
            String errorString = translator.toLocale(error.getDefaultMessage());
            listErrors.add(errorString);
        });
        info.setErrorMessage(listErrors);
        info.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return info;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionInfo handleAccessDeniedException(AccessDeniedException exception){
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        return new ExceptionInfo(listErrors, HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionInfo handleAuthenticationException(AuthenticationException exception){
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        return new ExceptionInfo(listErrors, HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionInfo handleBadCredentialsException(BadCredentialsException exception) {
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        return new ExceptionInfo(listErrors, HttpStatus.UNAUTHORIZED.value());
    }

}
