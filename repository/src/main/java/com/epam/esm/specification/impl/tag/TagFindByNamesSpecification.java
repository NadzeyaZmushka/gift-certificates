package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.List;

import static java.util.stream.Collectors.joining;

//todo ???
public class TagFindByNamesSpecification extends BaseSqlSpecification<Tag> {

    private final List<String> tagNames;
    public TagFindByNamesSpecification(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.tag ";
    }

    @Override
    public String getWhereCondition() {
        return String.format("name IN (%s)", tagNames.stream()
                .collect(joining("','", "'", "'")));
    }

    @Override
    public Object[] getParameters() {
        return tagNames.toArray();
    }
}