package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/orders")
public interface OrderController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<OrderDTO> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrderDTO findOne(@PathVariable Long id);

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity<Void> add(@RequestBody OrderDTO orderDTO);

}
