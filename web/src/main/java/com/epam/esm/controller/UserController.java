package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAll(@RequestParam(required = false, name = "page") int page,
                          @RequestParam(required = false, name = "limit") int limit);

    @GetMapping("/withOrders")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAllWithOrders(@RequestParam(required = false, name = "page") int page,
                                    @RequestParam(required = false, name = "limit") int limit);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO findOne(@PathVariable Long id);

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createOrder(@PathVariable Long id,
                                     @RequestBody String certificateName);

}
