package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.EntityRepository;
import com.epam.esm.repository.specification.SqlSpecification;
import com.epam.esm.repository.specification.TagSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements EntityRepository<Tag, TagSpecification> {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_TAGS_SQL = "SELECT tag_id, tag_name FROM gift_certificates.tag";
    private static final String ADD_TAG_SQL = "INSERT INTO gift_certificates.tag (name) VALUES (?)";
    private static final String FIND_TAG_BY_ID_SQL = "SELECT tag_id, tag_name FROM gift_certificates.tag WHERE tag_id = ?";
    private static final String UPDATE_TAG_SQL = "UPDATE gift_certificates.tag SET name = ? WHERE id = ?";
    private static final String DELETE_TAG_SQL = "DELETE FROM gift_certificates.tag WHERE id = ?";

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //todo: параметризовать, вынести в абстрактный класс
    @Override
    public List<Tag> queryForList(SqlSpecification specification) {
        return jdbcTemplate.queryForList(specification.toSql(), Tag.class, specification.getParameters());
    }

    @Override
    public Tag queryForOne(SqlSpecification specification) {
        return jdbcTemplate.queryForObject(specification.toSql(), Tag.class, specification.getParameters());
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
    public boolean remove(Tag entity) {
        return jdbcTemplate.update(DELETE_TAG_SQL, entity.getId()) > 0;
    }

}
