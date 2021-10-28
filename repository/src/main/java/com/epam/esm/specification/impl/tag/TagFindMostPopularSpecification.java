package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.Map;

//todo
public class TagFindMostPopularSpecification extends BaseSqlSpecification<Tag> {

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.tag";
    }

    @Override
    public Map<String, String> getJoinConditions() {
        return super.getJoinConditions();
    }

    @Override
    public String getWhereCondition() {
        return "";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

}
