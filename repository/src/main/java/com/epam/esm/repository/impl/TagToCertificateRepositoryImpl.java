package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.TagToCertificateRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import com.epam.esm.repository.specification.SqlSpecification;
import com.epam.esm.repository.specification.impl.FindByNameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagToCertificateRepositoryImpl extends BaseCrudRepository<Tag> implements TagToCertificateRepository {

    private static final String CREATE_CERTIFICATE_TAG_SQL = "INSERT INTO gifts.certificate_tag " +
            "(certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_CERTIFICATE_TAG_SQL = "DELETE FROM gifts.certificate_tag " +
            "WHERE tag_id = ? AND certificate_id = ?";

    private final TagRepository tagRepository;

    @Autowired
    public TagToCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, EntityMapper<Tag> mapper, TagRepository tagRepository) {
        super(jdbcTemplate, mapper);
        this.tagRepository = tagRepository;
    }

    @Override
    public void addTagToCertificate(Long certificatedId, Tag tag) {
        Tag existTag = tagRepository.findByName(
                new FindByNameSpecification("tag", tag.getName()))
                .orElse(null);
        if (existTag == null) {
            existTag = tagRepository.add(tag);
        } else {
            tag.setId(existTag.getId());
        }
        jdbcTemplate.update(CREATE_CERTIFICATE_TAG_SQL, certificatedId, existTag.getId());
    }

    @Override
    public void deleteTagFromCertificate(Long certificatedId, Tag tag) {
        jdbcTemplate.update(DELETE_CERTIFICATE_TAG_SQL, tag.getId(), certificatedId);
    }

    @Override
    protected String getTableName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> queryForList(SqlSpecification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> queryForOne(SqlSpecification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> findByName(SqlSpecification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tag add(Tag entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Tag entity) {
        throw new UnsupportedOperationException();
    }
}
