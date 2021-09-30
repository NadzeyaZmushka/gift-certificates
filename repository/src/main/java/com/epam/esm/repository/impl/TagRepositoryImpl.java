package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.EntityRepository;
import com.epam.esm.repository.mapper.TagMapper;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements EntityRepository<Tag> {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_TAGS_SQL = "SELECT tag_id, tag_name FROM gift_certificates.tag";
    private static final String ADD_TAG_SQL = "INSERT INTO gift_certificates.tag (tag_name) VALUES (?)";
    private static final String FIND_TAG_BY_ID_SQL = "SELECT tag_id, tag_name FROM gift_certificates.tag WHERE tag_id = ?";
    private static final String UPDATE_TAG_SQL = "UPDATE gift_certificates.tag SET tag_name = ? WHERE tag_id = ?";
    private static final String DELETE_TAG_SQL = "DELETE FROM gift_certificates.tag WHERE tag_id = ?";

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //todo:
    @Override
    public List<Tag> query(SqlSpecification specification) {
        return null;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_SQL, new TagMapper());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(FIND_TAG_BY_ID_SQL, new TagMapper()).stream().findFirst();
    }

    @Override
    public Tag add(Tag entity) {
        jdbcTemplate.update(ADD_TAG_SQL, entity.getName());
        return entity;
    }

    @Override
    public boolean update(Tag entity) {
        return jdbcTemplate.update(UPDATE_TAG_SQL, entity.getName(), entity.getId()) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TAG_SQL, id) > 0;
    }

}
