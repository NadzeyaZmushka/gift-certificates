package com.epam.esm.repository.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

    private static final String TAG_ID = "tag_id";
    private static final String TAG_NAME = "tag_name";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(TAG_ID));
        tag.setName(rs.getString(TAG_NAME));
        return tag;
    }

}
