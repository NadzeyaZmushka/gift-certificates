package com.epam.esm.controller.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.OrderConverter;
import com.epam.esm.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderService;
    private final OrderConverter mapper;

    @Override
    public List<OrderDTO> findAll() {
        return orderService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findOne(Long id) {
        return mapper.toDTO(orderService.findById(id));
    }

//    @Override
//    public ResponseEntity<Void> add(OrderDTO orderDTO) {
//        Order order = orderService.add(mapper.toEntity(orderDTO));
//        URI location = URI.create(String.format("/orders/%d", order.getId()));
//        return ResponseEntity.created(location).build();
//    }

}
