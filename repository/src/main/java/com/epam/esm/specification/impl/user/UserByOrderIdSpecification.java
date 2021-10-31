package com.epam.esm.specification.impl.user;

import com.epam.esm.entity.User;
import com.epam.esm.specification.BaseSqlSpecification;

public class UserByOrderIdSpecification extends BaseSqlSpecification<User> {

    private final Long orderId;

    public UserByOrderIdSpecification(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getBaseStatement() {
        return null;
    }

    @Override
    public String toSql() {
        return "SELECT * FROM gifts.user LEFT JOIN gifts.order ON \"user\".id = \"order\".user_id WHERE \"order\".id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{orderId};
    }

}
