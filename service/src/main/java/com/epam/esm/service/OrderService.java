package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService extends EntityService<Order> {

    /**
     * Finds all users orders
     *
     * @param userId   user id
     * @param page     page
     * @param pageSize limit
     * @return list of user orders
     */
    List<Order> findAllByUserId(Long userId, int page, int pageSize);

    /**
     * Create order with this certificate for user with such id
     *
     * @param userId        user id
     * @param certificateId certificate id
     * @return new order with id
     */
    Order create(Long userId, Long certificateId);

    Long countFoundOrders(Long userId);

}
