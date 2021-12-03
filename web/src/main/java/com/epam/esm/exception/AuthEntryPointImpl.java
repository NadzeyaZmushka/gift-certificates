package com.epam.esm.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthEntryPointImpl implements AuthenticationEntryPoint {

    //будет вызван, если пользователь попытается получить доступ к конечной точке, требующей аутентификации
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        ExceptionInfo info = new ExceptionInfo(List.of("Forbidden"), HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(mapper.writeValueAsString(info));
    }
}