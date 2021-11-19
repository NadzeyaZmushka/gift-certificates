package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;
    private final UserConverter converter;
    private final UserLinkBuilder hateoasLinkBuilder;
    private final OrderLinkBuilder orderLinkBuilder;

    @Override
    public PagedModel<UserDTO> findAll(int page, int limit) {
        List<UserDTO> users = userService.findAll(limit, page)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        users.forEach(hateoasLinkBuilder::addLinksForUser);
        Long count = userService.count();
        PagedModel<UserDTO> pagedModel = PagedModel.of(users, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel);
        return pagedModel;
    }

    @Override
    public UserDTO findOne(Long id) {
        UserDTO userDTO = converter.toDTO(userService.findById(id));
        hateoasLinkBuilder.addLinksForUser(userDTO);
        userDTO.getOrders().forEach(orderLinkBuilder::addLinksForOrder);
        return userDTO;
    }

}
