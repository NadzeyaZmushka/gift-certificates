package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final ModelMapper modelMapper;

    public Order toEntity(OrderDTO orderDTO) {
        return Objects.isNull(orderDTO) ? null : modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO toDTO(Order order) {
        return Objects.isNull(order) ? null : modelMapper.map(order, OrderDTO.class);
    }

}
