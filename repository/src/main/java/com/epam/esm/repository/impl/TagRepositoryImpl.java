package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.EntityRepository;
import com.epam.esm.repository.mapper.TagMapper;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements EntityRepository<Tag> {


    private final JdbcTemplate jdbcTemplate;

//    private static final String FIND_ALL_TAGS_SQL = "SELECT id, name FROM gift_certificates.tag";
    private static final String ADD_TAG_SQL = "INSERT INTO gift_certificates.tag (name) VALUES (?)";
//    private static final String FIND_TAG_BY_ID_SQL = "SELECT id, name FROM gift_certificates.tag WHERE id = ?";
    private static final String UPDATE_TAG_SQL = "UPDATE gift_certificates.tag SET name = ? WHERE id = ?";
    private static final String DELETE_TAG_SQL = "DELETE FROM gift_certificates.tag WHERE id = ?";

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag queryForOne(SqlSpecification specification) {
        return jdbcTemplate.query(specification.toSql(), new TagMapper(), specification.getParameters())
                .stream()
                .findFirst()
                .orElse(null);
    }

    //todo: параметризовать, вынести в абстрактный класс
    @Override
    public List<Tag> queryForList(SqlSpecification specification) {
        return jdbcTemplate.query(specification.toSql(), new TagMapper(), specification.getParameters());
    }

    @Override
    public void add(Tag entity) {
        jdbcTemplate.update(ADD_TAG_SQL, entity.getName());
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
