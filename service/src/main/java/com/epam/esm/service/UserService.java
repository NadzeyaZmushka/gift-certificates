package com.epam.esm.service;

import com.epam.esm.entity.User;

public interface UserService extends EntityService<User> {

    public User findByOrderId(Long orderId);

}