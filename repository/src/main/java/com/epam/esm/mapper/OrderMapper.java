package com.epam.esm.mapper;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderMapper implements EntityMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        order.setCost(rs.getBigDecimal("cost"));
        //todo
        return order;
    }
}
