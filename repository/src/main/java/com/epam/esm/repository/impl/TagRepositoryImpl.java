package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class TagRepositoryImpl extends BaseCrudRepository<Tag> implements TagRepository {

    private static final String ADD_TAG_SQL = "INSERT INTO gifts.tag (name) VALUES (?)";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT * FROM gifts.tag tag " +
            "JOIN gifts.certificate_tag join_table ON tag.id = join_table.tag_id " +
            "WHERE join_table.certificate_id = ?";

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, EntityMapper<Tag> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    public Tag add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        Long id = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        tag.setId(id);
        return tag;
    }

    @Override
    protected String getTableName() {
        return "tag";
    }

    @Override
    public List<Tag> findTagsByGiftCertificateId(Long id) {
        return jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, mapper, id);
    }

}
