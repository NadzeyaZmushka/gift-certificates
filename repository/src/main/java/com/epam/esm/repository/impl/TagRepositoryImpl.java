package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends BaseCrudRepository<Tag> implements TagRepository {

    private static final String ADD_TAG_SQL = "INSERT INTO gift_certificates.tag (name) VALUES (:name)";
    private static final String UPDATE_TAG_SQL = "UPDATE gift_certificates.tag SET name = :name WHERE id = :id";
    private static final String DELETE_TAG_SQL = "DELETE FROM gift_certificates.tag WHERE id = :id";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT * FROM gift_certificates.tag tag " +
            "JOIN gift_certificates.certificate_tag join_table ON tag.id = join_table.tag_id " +
            "WHERE join_table.certificate_id = :c_id";

    @Autowired
    public TagRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, EntityMapper<Tag> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    protected String getSqlQueryCreate() {
        return ADD_TAG_SQL;
    }

    @Override
    protected String getSqlQueryUpdate(Tag tag) {
        return UPDATE_TAG_SQL;
    }

    @Override
    protected String getSqlQueryDelete() {
        return DELETE_TAG_SQL;
    }

    @Override
    protected SqlParameterSource getSqlParameterSource(Tag tag) {
        return new MapSqlParameterSource()
                .addValue("id", tag.getId())
                .addValue("name", tag.getName());
    }

    @Override
    protected SqlParameterSource getSqlParameterSourceForUpdate(Tag tag) {
        return getSqlParameterSource(tag);
    }

    @Override
    public List<Tag> findTagsByGiftCertificateId(Long id) {
        return jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new MapSqlParameterSource("c_id", id), mapper);
    }

    //todo
    @Override
    public Optional<Tag> isPresent(String name) {
        return Optional.empty();
    }

}
