package com.epam.esm.mapper;

import com.epam.esm.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

/**
 * Interface for mapping information from
 * result set row to the database entity
 *
 * @param <T> database entity
 */
public interface EntityMapper<T extends Entity> extends RowMapper<T> {
}
