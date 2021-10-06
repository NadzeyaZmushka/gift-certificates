package com.epam.esm.repository.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements EntityMapper<Tag> {

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(TAG_ID));
        tag.setName(rs.getString(TAG_NAME));
        return tag;
    }

}
