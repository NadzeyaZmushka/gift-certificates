package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService extends EntityService<Order> {

    List<Order> findByUserId(Long userId);

    Order create(Long userId, String certificateName);

}
