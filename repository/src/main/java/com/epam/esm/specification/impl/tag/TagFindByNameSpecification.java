package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a tag by its name in database
 *
 * @author Nadzeya Zmushka
 */
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
