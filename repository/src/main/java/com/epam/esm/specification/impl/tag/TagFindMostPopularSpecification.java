package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.Map;

public class TagFindMostPopularSpecification extends BaseSqlSpecification<Tag> {

    @Override
    public String getBaseStatement() {
        return null;
    }

    @Override
    public String toSql() {
        return "SELECT tag.id, tag.name\n" +
                "FROM gifts.tag\n" +
                "         INNER JOIN gifts.certificate_tag ct on tag.id = ct.tag_id\n" +
                "         INNER JOIN gifts.certificate c on c.id = ct.certificate_id\n" +
                "         INNER JOIN gifts.\"order\" o on c.id = o.certificate_id\n" +
                "         INNER JOIN gifts.\"user\" u on u.id = o.user_id\n" +
                "WHERE u.id IN (\n" +
                "    SELECT wu.user_id\n" +
                "    FROM (\n" +
                "             SELECT SUM(gifts.\"order\".cost) sumCost, user_id\n" +
                "             FROM gifts.\"order\"\n" +
                "             GROUP BY user_id\n" +
                "             ORDER BY sumCost desc\n" +
                "             limit 1) as wu\n" +
                ")\n" +
                "GROUP BY tag.id\n" +
                "ORDER BY count(tag.id) desc\n" +
                "limit 1";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

}
