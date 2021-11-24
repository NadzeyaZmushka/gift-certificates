package com.epam.esm.repository.analytic;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagAnalyticMapper<T> implements RowMapper<TagAnalytic> {

    @Override
    public TagAnalytic mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagAnalytic analytic = new TagAnalytic();
        analytic.setUserId(rs.getLong("u_id"));
        analytic.setTag(new Tag(rs.getLong("id"), rs.getString("name")));
        analytic.setCount(rs.getInt("count"));
        return analytic;
    }
}
