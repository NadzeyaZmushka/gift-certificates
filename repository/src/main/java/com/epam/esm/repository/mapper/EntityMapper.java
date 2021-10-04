package com.epam.esm.repository.mapper;

import com.epam.esm.entity.BaseEntity;
import org.springframework.jdbc.core.RowMapper;

public interface EntityMapper<T extends BaseEntity> extends RowMapper<T> {

}
