package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.repository.BaseCrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository extends BaseCrudRepository<User> {

    private static final String NOT_SUPPORTED = "This operation is not supported";

    public UserRepository(JdbcTemplate jdbcTemplate, EntityMapper<User> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    protected String getTableName() {
        return "user";
    }

    @Override
    protected PreparedStatement prepareAddStatement(Connection connection, User entity) throws SQLException {
        return null;
    }

    @Override
    protected String getUpdateSql() {
        return null;
    }

    @Override
    protected Object[] getParam(User entity) {
        return new Object[0];
    }

    @Override
    public void addAll(List<User> tagCertificateList) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void removeAll(List<User> tagCertificateList) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

}
