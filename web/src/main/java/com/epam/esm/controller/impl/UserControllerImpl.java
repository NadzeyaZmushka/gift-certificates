package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.HateoasLinkBuilder;
import com.epam.esm.mapper.UserConverter;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;
    private final UserConverter mapper;
    private final HateoasLinkBuilder hateoasLinkBuilder;

    @Override
    public List<UserDTO> findAll(int page, int limit) {
        List<UserDTO> userDTOList = userService.findAll(limit, page)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        userDTOList.forEach(hateoasLinkBuilder::addLinksForUser);
        return userDTOList;
    }

    @Override
    public List<UserDTO> findAllWithOrders(int page, int limit) {
        return userService.findAllWithOrders(limit, page)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findOne(Long id) {
        UserDTO userDTO = mapper.toDTO(userService.findById(id));
        hateoasLinkBuilder.addLinksForUser(userDTO);
        return userDTO;
    }

    //todo
    @Override
    public ResponseEntity<Void> createOrder(Long id, String certificateName) {
       orderService.create(id, certificateName);
        return ResponseEntity.ok().build();
    }

}
