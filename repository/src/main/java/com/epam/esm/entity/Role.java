package com.epam.esm.entity;

import javax.lang.model.UnknownEntityException;
import java.util.NoSuchElementException;


public enum Role {
    ROLE_ADMIN(1L),
    ROLE_USER(2L);

    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Role resolveRoleById(Long id) {
        for (Role role : values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        throw new NoSuchElementException("Role is not found");
    }
}
