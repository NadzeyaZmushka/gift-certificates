package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.converter.OrderConverter;
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
    public PagedModel<OrderDTO> findAll(int page, int limit) {
        List<OrderDTO> orderDTOList = orderService.findAll(limit, page)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        orderDTOList.forEach(hateoasLinkBuilder::addLinksForOrder);
        Long count = orderService.count();
        PagedModel<OrderDTO> pagedModel = PagedModel.of(orderDTOList, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel);
        return pagedModel;
    }

    @Override
    public OrderDTO findOne(Long id) {
        OrderDTO orderDTO = converter.toDTO(orderService.findById(id));
        hateoasLinkBuilder.addLinksForOrder(orderDTO);
        return orderDTO;
    }

//    @Override
//    public List<OrderDTO> findAllByUserId(Long id) {
//        List<OrderDTO> orderDTOList = orderService.findByUserId(id, 1, 10)
//                .stream()
//                .map(mapper::toDTO)
//                .collect(Collectors.toList());
//        orderDTOList.forEach(hateoasLinkBuilder::addLinksForOrder);
//        return orderDTOList;
//    }

    @Override
    public ResponseEntity<Void> create(Long userId, Long certificateId) {
        Order order = orderService.create(userId, certificateId);
        URI location = URI.create(String.format("/orders/%d", order.getId()));
        return ResponseEntity.created(location).build();
    }

}
