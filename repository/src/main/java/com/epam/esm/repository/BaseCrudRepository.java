package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.specification.SqlSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * Implementation of the {@link CrudRepository} class uses JDBC to interact with database
 *
 * @param <T> entities which repository operates with
 * @author Nadzeya Zmushka
 */
@RequiredArgsConstructor
public abstract class BaseCrudRepository<T extends BaseEntity> implements CrudRepository<T> {

    protected final JdbcTemplate jdbcTemplate;
    protected final EntityMapper<T> mapper;

    private static final String DELETE_SQL = "DELETE FROM gifts.%s WHERE id = ?";

    protected abstract String getTableName();

    @Override
    public List<T> queryForList(SqlSpecification<T> specification) {
        try {
            return jdbcTemplate.query(specification.toSql(), mapper, specification.getParameters());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<T> queryForList(SqlSpecification<T> specification, QueryOptions options) {
        if (options == null) {
            return this.queryForList(specification);
        }
        StringBuilder queryBuilder = new StringBuilder(specification.toSql());
        ofNullable(options.getOrder())
                .ifPresent(order -> {
                    String parameters = order.entrySet().stream()
                            .map(e -> String.format("%s %s", e.getKey(), e.getValue()))
                            .collect(Collectors.joining(","));
                    queryBuilder.append(String.format(" ORDER BY %s", parameters));
                });
        ofNullable(options.getLimit())
                .ifPresent(limit -> {
                    queryBuilder.append(" limit ").append(limit);
                    ofNullable(options.getOffset())
                            .ifPresent(offset -> queryBuilder.append(" offset ").append(offset));
                });

        return jdbcTemplate.query(queryBuilder.toString(), mapper, specification.getParameters());
    }

    @Override
    public Optional<T> queryForOne(SqlSpecification<T> specification) {
        try {
            return ofNullable(jdbcTemplate.queryForObject(specification.toSql(), mapper, specification.getParameters()));
        } catch (EmptyResultDataAccessException e) {
            return empty();
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

    @Override
    public T update(T entity) {
        jdbcTemplate.update(getUpdateSql(), getParam(entity));

        return entity;
    }

    @Override
    public boolean remove(T entity) {
        return jdbcTemplate.update(String.format(DELETE_SQL, getTableName()), entity.getId()) != 0;
    }

    /**
     * Build prepared statement for query
     *
     * @param connection connection
     * @param entity     entity
     * @return prepared statement
     */
    protected abstract PreparedStatement prepareAddStatement(Connection connection, T entity) throws SQLException;

    /**
     * Gets sql query for update operation
     *
     * @return string sql
     */
    protected abstract String getUpdateSql();

    /**
     * Gets parameters for query
     *
     * @param entity entity
     * @return Object[] with parameters
     */
    protected abstract Object[] getParam(T entity);

}
