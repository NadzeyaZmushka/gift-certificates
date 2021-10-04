package com.epam.esm.repository.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseCrudRepository<T extends BaseEntity> implements CrudRepository<T> {

    protected final NamedParameterJdbcTemplate jdbcTemplate;
    protected final EntityMapper<T> mapper;

    public BaseCrudRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityMapper<T> mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    protected abstract String getSqlQueryCreate();

    protected abstract String getSqlQueryUpdate(T entity);

    protected abstract String getSqlQueryDelete();

    protected abstract SqlParameterSource getSqlParameterSource(T entity);

    protected abstract SqlParameterSource getSqlParameterSourceForUpdate(T entity);

    @Override
    public List<T> queryForList(SqlSpecification specification) {
        return jdbcTemplate.query(specification.toSql(), specification.getParameters(), mapper);
    }

    @Override
    public Optional<T> queryForOne(SqlSpecification specification) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(specification.toSql(), specification.getParameters(), mapper));
    }

    @Override
    public T add(T entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getSqlQueryCreate(), getSqlParameterSource(entity), keyHolder);
        Long id = (Long)Objects.requireNonNull(keyHolder.getKeys()).get("id");
        entity.setId(id);
        return entity;
    }

    @Override
    public boolean update(T entity) {
        return jdbcTemplate.update(getSqlQueryUpdate(entity)
                , getSqlParameterSourceForUpdate(entity)) != 0;
    }

    @Override
    public boolean remove(T entity) {
        return jdbcTemplate.update(getSqlQueryDelete()
                , new MapSqlParameterSource("id", entity.getId())) != 0;
    }
}
