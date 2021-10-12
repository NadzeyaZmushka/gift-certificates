package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.SqlSpecification;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//todo ???
public class TagFindByNamesSpecification extends EntitySpecification {

    private final List<String> tagNames;

    public TagFindByNamesSpecification(List<String> tagNames) {
        super("tag");
        this.tagNames = tagNames;
    }

    //
    @Override
    protected String getWhereStatement() {
        return String.format("WHERE name IN (%s)", tagNames.stream()
                .collect(Collectors.joining("','", "'", "'")));
    }

    @Override
    public Object[] getParameters() {
        return tagNames.toArray();
    }

}
