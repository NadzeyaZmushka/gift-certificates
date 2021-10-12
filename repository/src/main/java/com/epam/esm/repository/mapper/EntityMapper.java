package com.epam.esm.repository.mapper;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

public interface EntityMapper<T extends Entity> extends RowMapper<T> {

}
