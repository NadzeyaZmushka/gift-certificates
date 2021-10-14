package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

public class TagFindByIdSpecification extends BaseSqlSpecification<Tag> {

    private final Long id;

    public TagFindByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * from gifts.tag";
    }

    @Override
    public String getWhereCondition() {
        return "id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }
}
