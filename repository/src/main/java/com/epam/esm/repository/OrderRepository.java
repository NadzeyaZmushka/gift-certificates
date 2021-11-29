package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order> {

    /**
     * Find users orders
     *
     * @param user     User
     * @param page     page
     * @param pageSize limit
     * @return list of users orders
     */
    List<Order> findByUser(User user, int page, int pageSize);

}
