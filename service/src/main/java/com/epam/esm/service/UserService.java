package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    Optional<User> findByEmail(String email);
}
