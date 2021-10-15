package com.epam.esm.mapper;

import com.epam.esm.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

public interface EntityMapper<T extends Entity> extends RowMapper<T> {
}
