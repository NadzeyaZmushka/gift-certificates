package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/orders")
public interface OrderController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                           @RequestParam(required = false, name = "limit", defaultValue = "1000") int limit);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrderDTO findOne(@PathVariable Long id);

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<OrderDTO> findAllByUserId(@PathVariable Long id);

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity<Void> add(@RequestBody OrderDTO orderDTO);

}
