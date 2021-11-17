package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.OrderCreateRequestDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderService;
    private final OrderConverter converter;
    private final OrderLinkBuilder hateoasLinkBuilder;

    @Override
    public PagedModel<OrderDTO> findAll(int page, int limit, Long userId) {
        List<Order> orderList = orderService.findAllByUserId(userId, page, limit);
        List<OrderDTO> orders = orderList.stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        orders.forEach(hateoasLinkBuilder::addLinksForOrder);
        Long count = orderService.count();
        PagedModel<OrderDTO> pagedModel = PagedModel.of(orders, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel, userId);
        return pagedModel;
    }

    @Override
    public OrderDTO findOne(Long id) {
        OrderDTO orderDTO = converter.toDTO(orderService.findById(id));
        hateoasLinkBuilder.addLinksForOrder(orderDTO);
        return orderDTO;
    }

    @Override
    public ResponseEntity<Void> create(OrderCreateRequestDTO orderDTO) {
        Order order = orderService.create(orderDTO.getUserId(), orderDTO.getCertificateId());
        URI location = URI.create(String.format("/orders/%d", order.getId()));
        return ResponseEntity.created(location).build();
    }

}
