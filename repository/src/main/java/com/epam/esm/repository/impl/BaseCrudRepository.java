package com.epam.esm.repository.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import com.epam.esm.repository.specification.SqlSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseCrudRepository<T extends BaseEntity> implements CrudRepository<T> {

    protected final JdbcTemplate jdbcTemplate;
    protected final EntityMapper<T> mapper;

    private static final String DELETE_SQL = "DELETE FROM gifts.%s WHERE id = ?";

    protected abstract String getTableName();

    @Override
    public List<T> queryForList(SqlSpecification specification) {
        return jdbcTemplate.query(specification.toSql(), mapper, specification.getParameters());
    }

//    @Override
//    public List<T> queryForList(SqlSpecification specification, ByCriteriaSpecification options) {
//        return null;
//    }

    @Override
    public Optional<T> queryForOne(SqlSpecification specification) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(specification.toSql(), mapper, specification.getParameters()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public T add(T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> prepareAddStatement(con, entity), keyHolder);
        Long id = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        entity.setId(id);
        return entity;
    }


    // не правильно сделали?
    @Override
    public T update(T entity) {
        jdbcTemplate.update(getUpdateSql(), getParam(entity));
        return entity;
    }

    //todo try-catch
    @Override
    public boolean remove(T entity) {
        return jdbcTemplate.update(String.format(DELETE_SQL, getTableName()), entity.getId()) != 0;
    }

    protected abstract PreparedStatement prepareAddStatement(Connection connection, T entity) throws SQLException;

    protected abstract String getUpdateSql();

    protected abstract Object[] getParam(T entity);
}
