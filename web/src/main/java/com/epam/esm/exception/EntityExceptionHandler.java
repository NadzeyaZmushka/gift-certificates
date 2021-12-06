package com.epam.esm.exception;

import com.epam.esm.configuration.Translator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice("com.epam.esm.controller")
@RequiredArgsConstructor
public class EntityExceptionHandler implements AuthenticationEntryPoint {

    private final Translator translator;

    //будет вызван, если пользователь попытается получить доступ к конечной точке, требующей аутентификации
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        ExceptionInfo info = new ExceptionInfo(List.of("Forbidden"), HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(mapper.writeValueAsString(info));
    }

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


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionInfo handleBadCredentialsException(BadCredentialsException exception) {
        List<String> listErrors = new ArrayList<>();
        listErrors.add(exception.getMessage());
        return new ExceptionInfo(listErrors, HttpStatus.UNAUTHORIZED.value());
    }

}
