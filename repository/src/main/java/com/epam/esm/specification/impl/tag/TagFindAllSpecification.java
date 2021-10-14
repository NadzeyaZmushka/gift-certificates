package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

public class TagFindAllSpecification extends BaseSqlSpecification<Tag> {

    @Override
    public String getBaseStatement() {
        return "SELECT * from gifts.tag";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
