package com.epam.esm.controller;

import com.epam.esm.dto.OrderCreateRequestDTO;
import com.epam.esm.dto.OrderDTO;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/orders")
public interface OrderController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ROLE_ADMIN') OR authentication.principal.id == #userId")
    PagedModel<OrderDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                 @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
                                 @RequestParam(required = false, name = "userId") Long userId);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrderDTO findOne(@PathVariable Long id);


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> create(@RequestBody OrderCreateRequestDTO orderDTO);

}
