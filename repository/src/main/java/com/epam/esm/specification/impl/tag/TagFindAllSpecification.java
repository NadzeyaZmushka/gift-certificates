package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a collection of all tags in database
 *
 * @author Nadzeya Zmushka
 */
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
