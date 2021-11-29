package com.epam.esm.controller;

import com.epam.esm.dto.AuthResponse;
import com.epam.esm.dto.SignupRequest;
import com.epam.esm.entity.User;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserServiceImpl userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody @Valid SignupRequest request) {
        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        userService.add(user);
        return "OK";
    }

    //todo re-fresh token
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authenticate(@RequestHeader(name = "Authorization") String auth) {
        String encoded = auth.substring(6);
        byte[] decoded = Base64.getDecoder().decode(encoded);
        String stringDecoded = new String(decoded);
        String[] emailAndPassword = stringDecoded.split(":");
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return userService.findByEmail(email)
                .map(user -> {
                    String token = jwtProvider.generateToken(user.getEmail(), user.getUserRole().name());
                    return new AuthResponse(user.getId(), user.getUserRole(), token);
                }).orElseGet(AuthResponse::new);
    }

}
