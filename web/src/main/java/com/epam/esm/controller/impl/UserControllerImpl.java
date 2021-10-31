package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.UserConverter;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;
    private final UserConverter mapper;

    @Override
    public List<UserDTO> findAll() {
        return userService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findOne(Long id) {
        return mapper.toDTO(userService.findById(id));
    }

    //todo
    @Override
    public ResponseEntity<Void> createOrder(Long id, String certificateName) {
       orderService.create(id, certificateName);
        return ResponseEntity.ok().build();
    }

}
