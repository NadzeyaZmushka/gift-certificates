package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.SignupRequest;
import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedModel<UserDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                @RequestParam(required = false, name = "limit", defaultValue = "10") int limit);


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //проверять разрешения перед обработкой запроса
    @PreAuthorize("hasRole('ROLE_ADMIN') OR authentication.principal.claims['user_id'] == #id")
    UserDTO findOne(@PathVariable Long id);

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR authentication.principal.claims['user_id'] == #id")
    UserDTO update(@PathVariable("id") long id, @RequestBody UserDTO userDTO);

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    //проверять разрешения перед обработкой запроса
    @PreAuthorize("hasRole('ROLE_ADMIN') OR authentication.principal.claims['user_id'] == #id")
    PagedModel<OrderDTO> findAllByUser(@PathVariable Long id, @RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                       @RequestParam(required = false, name = "limit", defaultValue = "10") int limit);

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> registerUser(@RequestBody @Valid SignupRequest request);

}
