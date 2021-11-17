package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedModel<UserDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                @RequestParam(required = false, name = "limit", defaultValue = "10") int limit);


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO findOne(@PathVariable Long id);

}