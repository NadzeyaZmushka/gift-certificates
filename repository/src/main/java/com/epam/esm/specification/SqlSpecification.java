package com.epam.esm.specification;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Entity;

public interface SqlSpecification<T extends Entity> {

    String toSql();

    Object[] getParameters();

}
