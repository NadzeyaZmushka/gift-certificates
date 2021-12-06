package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.converter.OrderConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.CertificateLinkBuilder;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final UserConverter converter;
    private final OrderConverter orderConverter;
    private final UserLinkBuilder hateoasLinkBuilder;
    private final OrderLinkBuilder orderLinkBuilder;
    private final CertificateLinkBuilder certificateLinkBuilder;

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
        return userDTO;
    }

    @Override
    public UserDTO update(long id, UserDTO userDTO) {
        return converter.toDTO(userService.update(id, converter.toEntity(userDTO)));
    }

    @Override
    public PagedModel<OrderDTO> findAllByUser(Long id, int page, int limit) {
        List<OrderDTO> orders = orderService.findAllByUserId(id, page, limit).stream()
                .map(orderConverter::toDTO)
                .collect(Collectors.toList());
        orders.forEach(orderLinkBuilder::addLinksForOrder);
        for (OrderDTO orderDTO : orders) {
            certificateLinkBuilder.addLinksForCertificate(orderDTO.getCertificate());
        }
        Long count = orderService.countFoundOrders(id);
        PagedModel<OrderDTO> pagedModel = PagedModel.of(orders, new PagedModel.PageMetadata(limit, page, count));
        orderLinkBuilder.createPaginationLinks(pagedModel, id);
        return pagedModel;

    }
}
