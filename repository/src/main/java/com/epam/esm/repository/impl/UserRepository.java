package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.impl.order.OrderFindByUserIdSpecification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseCrudRepository<User> {

    private static final String ADD_USER_SQL = "INSERT INTO gifts.user (name, surname) " +
            "VALUES (?, ?)";
    private static final String UPDATE_USER_SQL = "UPDATE gifts.user SET name = ?, " +
            "surname = ?";
    private static final String NOT_SUPPORTED = "This operation is not supported";

    private final OrderRepository orderRepository;

    public UserRepository(JdbcTemplate jdbcTemplate, EntityMapper<User> mapper, OrderRepository orderRepository) {
        super(jdbcTemplate, mapper);
        this.orderRepository = orderRepository;
    }

    @Override
    protected String getTableName() {
        return "user";
    }

    @Override
    public List<User> queryForList(SqlSpecification<User> specification) {
        List<User> users = super.queryForList(specification);
        users.forEach(this::addOrdersToUser);
        return users;
    }

    @Override
    public Optional<User> queryForOne(SqlSpecification<User> specification) {
        Optional<User> user = super.queryForOne(specification);
        user.ifPresent(this::addOrdersToUser);
        return user;
    }

    @Override
    protected PreparedStatement prepareAddStatement(Connection connection, User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        return ps;
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_USER_SQL;
    }

    @Override
    protected Object[] getParam(User user) {
        return new Object[]{user.getName(),
                user.getName()};
    }

    @Override
    public void addAll(List<User> tagCertificateList) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void removeAll(List<User> tagCertificateList) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    private void addOrdersToUser(User user) {
        List<Order> orders = orderRepository.queryForList(new OrderFindByUserIdSpecification(user.getId()));
        user.setOrders(orders);
    }

}
