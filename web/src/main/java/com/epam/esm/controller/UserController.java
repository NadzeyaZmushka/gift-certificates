package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO findOne(@PathVariable Long id);


}
