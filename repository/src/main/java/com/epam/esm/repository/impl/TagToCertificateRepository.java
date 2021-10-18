package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagAndCertificate;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.QueryOptions;
import com.epam.esm.specification.SqlSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link BaseCrudRepository}
 *
 * @author Nadzeya Zmushka
 */
@Repository
@RequiredArgsConstructor
public class TagToCertificateRepository implements CrudRepository<TagAndCertificate> {

    private static final String CREATE_CERTIFICATE_TAG_SQL = "INSERT INTO gifts.certificate_tag " +
            "(certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_CERTIFICATE_TAG_SQL = "DELETE FROM gifts.certificate_tag " +
            "WHERE certificate_id = ? AND tag_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TagAndCertificate> queryForList(SqlSpecification<TagAndCertificate> specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TagAndCertificate> queryForList(SqlSpecification<TagAndCertificate> specification, QueryOptions options) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<TagAndCertificate> queryForOne(SqlSpecification<TagAndCertificate> specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TagAndCertificate update(TagAndCertificate entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TagAndCertificate add(TagAndCertificate entity) {
        jdbcTemplate.update(CREATE_CERTIFICATE_TAG_SQL, entity.getCertificateId(), entity.getTagId());
        return entity;
    }

    @Override
    public boolean remove(TagAndCertificate entity) {
        return jdbcTemplate.update(DELETE_CERTIFICATE_TAG_SQL, entity.getTagId(), entity.getCertificateId()) != 0;
    }

    @Override
    public void addAll(List<TagAndCertificate> tagCertificateList) {
        jdbcTemplate.batchUpdate(CREATE_CERTIFICATE_TAG_SQL, tagCertificateList.stream()
                .map(tc -> new Object[]{tc.getCertificateId(), tc.getTagId()})
                .collect(Collectors.toList()));
    }

    @Override
    public void removeAll(List<TagAndCertificate> tagCertificateList) {
        jdbcTemplate.batchUpdate(DELETE_CERTIFICATE_TAG_SQL, tagCertificateList.stream()
                .map(tc -> new Object[]{tc.getCertificateId(), tc.getTagId()})
                .collect(Collectors.toList()));
    }

}
