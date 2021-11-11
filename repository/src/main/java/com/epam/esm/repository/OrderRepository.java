package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order> {

    List<Order> findByUserId(User user, int page, int pageSize);

}
