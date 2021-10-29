package com.epam.esm.specification.impl.order;

import com.epam.esm.entity.Order;
import com.epam.esm.specification.BaseSqlSpecification;

public class OrderFindByUserIdSpecification extends BaseSqlSpecification<Order> {

    private final Long userId;

    public OrderFindByUserIdSpecification(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.order";
    }

    @Override
    public String getWhereCondition() {
        return "user_id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{userId};
    }

}
