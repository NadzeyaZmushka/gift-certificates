package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import com.epam.esm.repository.specification.impl.FindByNameSpecification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class CertificateRepositoryImpl extends BaseCrudRepository<Certificate> implements CertificateRepository {

    private static final String ADD_CERTIFICATE_SQL = "INSERT INTO gifts.certificate (name, description, price" +
            ", duration, create_date, last_update_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CERTIFICATE_SQL = "UPDATE gifts.certificate SET name = ?" +
            ", description = ?, price = ?, duration = ?" +
            ", create_date = ?, last_update_date = ? WHERE id = ?";
    private static final String CREATE_CERTIFICATE_TAG_SQL = "INSERT INTO gifts.certificate_tag " +
            "(certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_CERTIFICATE_TAG_SQL = "DELETE FROM gifts.certificate_tag " +
            "WHERE tag_id = ? AND certificate_id = ?";

    private final TagRepositoryImpl tagRepository;

    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, EntityMapper<Certificate> mapper, TagRepositoryImpl tagRepository) {
        super(jdbcTemplate, mapper);
        this.tagRepository = tagRepository;
    }

    @Override
    public Certificate add(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_CERTIFICATE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setObject(5, certificate.getCreateDate());
            ps.setObject(6, certificate.getLastUpdateDate());
            return ps;
        }, keyHolder);
        Long id = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        certificate.setId(id);
        List<Tag> tags = certificate.getTags();
        for (Tag tag : tags) {
            addTagToCertificate(certificate.getId(), tag);
        }
        return certificate;
    }

    @Override
    protected String getTableName() {
        return "certificate";
    }

    //?? в новый репозиторий
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

    //todo в новый репозиторий
    @Override
    public void deleteTagFromCertificate(Long certificatedId, Tag tag) {
        jdbcTemplate.update(DELETE_CERTIFICATE_TAG_SQL, tag.getId(), certificatedId);
    }

    //todo try-catch
    @Override
    public Certificate update(Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE_SQL, certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                certificate.getId());
        List<Tag> tags = certificate.getTags();
        for (Tag tag : tags) {
            addTagToCertificate(certificate.getId(), tag);
        }
        return certificate;
    }

}
