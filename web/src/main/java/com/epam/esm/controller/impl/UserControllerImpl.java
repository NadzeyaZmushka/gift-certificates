package com.epam.esm.controller.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.service.impl.OrderServiceImpl;
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
    private final OrderServiceImpl orderService;
    private final UserConverter converter;
    private final UserLinkBuilder hateoasLinkBuilder;

    @Override
    public PagedModel<UserDTO> findAll(int page, int limit) {
        List<UserDTO> userDTOList = userService.findAll(limit, page)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        userDTOList.forEach(hateoasLinkBuilder::addLinksForUser);
        Long count = userService.count();
        PagedModel<UserDTO> pagedModel = PagedModel.of(userDTOList, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel);
        return pagedModel;
    }

    @Override
    public UserDTO findOne(Long id) {
        UserDTO userDTO = converter.toDTO(userService.findById(id));
        hateoasLinkBuilder.addLinksForUser(userDTO);
        return userDTO;
    }

//    @Override
//    public ResponseEntity<Void> createOrder(Long id, String certificateName) {
//        Order order = orderService.create(id, , certificateName);
//        URI location = URI.create(String.format("/orders/%d", order.getId()));
//        return ResponseEntity.created(location).build();
//    }

}
