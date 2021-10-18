package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.repository.BaseCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Implementation of the {@link BaseCrudRepository}
 *
 * @author Nadzeya Zmushka
 */
@Repository
public class TagRepository extends BaseCrudRepository<Tag> {

    private static final String ADD_TAG_SQL = "INSERT INTO gifts.tag (name) VALUES (?)";
    private static final String UPDATE_TAG_SQL = "UPDATE gifts.tag SET name = ? WHERE id = ?";

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate, EntityMapper<Tag> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    public void addAll(List<Tag> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(List<Tag> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareAddStatement(Connection connection, Tag tag) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(ADD_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, tag.getName());
        return ps;
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_TAG_SQL;
    }

    @Override
    protected Object[] getParam(Tag entity) {
        return new Object[]{entity.getName(),
                entity.getId()};
    }

    @Override
    protected String getTableName() {
        return "tag";
    }

}
