package com.epam.esm.specification.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.BaseSqlSpecification;
import com.epam.esm.specification.SqlSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrSpecification<T extends BaseEntity> implements SqlSpecification<T> {

    private final List<BaseSqlSpecification<T>> specifications;

    @SafeVarargs
    public OrSpecification(BaseSqlSpecification<T>... specifications) {
        if (specifications.length <= 0) {
            throw new IllegalArgumentException("");
        }
        this.specifications = Arrays.asList(specifications);
    }

    @Override
    public String toSql() {
        StringBuilder query = new StringBuilder(specifications.get(0).getBaseStatement());
        query.append(" ");
        Map<String, String> joins = new HashMap<>();
        //взять все joins cond. и слить их в одну мапу
        specifications.forEach(spec -> joins.putAll(spec.getJoinConditions()));
        //общую мапу сджойнили как BaseSqlSpec.
        Optional.ofNullable(joins).ifPresent(join -> join.forEach(
                (key, value) -> query.append(String.format("JOIN %s ON %s ", key, value))
        ));
        //взять со всех спец. where cond. и объеденить через OR
        List<String> wheres = new ArrayList<>();
        specifications.forEach(spec -> Collections.addAll(wheres, spec.getWhereCondition()));
        Optional.ofNullable(wheres).ifPresent(spec -> spec.forEach(
                cond -> query.append(" WHERE ").append(cond)
        ));
        //...
        return query.toString();
    }

    @Override
    public Object[] getParameters() {
        List<Object> params = new ArrayList<>();
        specifications.forEach(spec -> Collections.addAll(params, spec.getParameters()));
        return params.toArray();
    }
}