package com.epam.esm.repository.specification;

import com.epam.esm.entity.BaseEntity;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface SqlSpecification {

    String toSql();

}
