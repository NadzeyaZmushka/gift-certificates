package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

public class TagFindByNameSpecification extends BaseSqlSpecification<Tag> {

    private final String name;

    public TagFindByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.tag";
    }

    @Override
    public String getWhereCondition() {
        return "name = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }
}
