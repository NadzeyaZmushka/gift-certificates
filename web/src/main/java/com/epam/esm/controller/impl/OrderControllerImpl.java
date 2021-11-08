package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.hateoas.HateoasLinkBuilder;
import com.epam.esm.mapper.OrderConverter;
import com.epam.esm.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderService;
    private final OrderConverter mapper;
    private final HateoasLinkBuilder hateoasLinkBuilder;

    @Override
    public List<OrderDTO> findAll(int page, int limit) {
        List<OrderDTO> orderDTOList = orderService.findAll(limit, page)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        orderDTOList.forEach(hateoasLinkBuilder::addLinksForOrder);
        return orderDTOList;
    }

    @Override
    public OrderDTO findOne(Long id) {
        OrderDTO orderDTO = mapper.toDTO(orderService.findById(id));
        hateoasLinkBuilder.addLinksForOrder(orderDTO);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllByUserId(Long id) {
        List<OrderDTO> orderDTOList = orderService.findByUserId(id, 1, 10)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        orderDTOList.forEach(hateoasLinkBuilder::addLinksForOrder);
        return orderDTOList;
    }

}
