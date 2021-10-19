package com.epam.esm.repository;

import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.specification.BaseSqlSpecification;
import com.epam.esm.specification.impl.AndSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByTagNameSpecification;
import com.epam.esm.specification.impl.certificate.CertificateFindAllSpecification;
import com.epam.esm.specification.impl.certificate.CertificateFindByIdSpecification;
import com.epam.esm.specification.impl.certificate.CertificateLikeNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class CertificateRepositoryTest {

    @Autowired
    private BaseCrudRepository<Certificate> certificateRepository;

    @Autowired
    private BaseCrudRepository<Tag> tagRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CertificateMapper certificateMapper;

    @Test
    void testShouldReturnAllCertificates() {
        List<Certificate> certificates = certificateRepository.queryForList(new CertificateFindAllSpecification());

        assertEquals(5, certificates.size());

        Certificate certificate1 = certificates.get(0);
        certificate1.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate1.getId())));

        assertEquals(1L, certificate1.getId());
        assertEquals("certificate1", certificate1.getName());

        List<Tag> tags = certificate1.getTags();

        assertEquals(2, tags.size());
    }

    @Test
    void testShouldReturnCertificateWithSuchId() {
        Optional<Certificate> certificate = certificateRepository.queryForOne(new CertificateFindByIdSpecification(1L));
        assertTrue(certificate.isPresent());
        assertEquals(1L, certificate.get().getId());
    }

    @Test
    void testShouldReturnAllCertificateByCriteria() {
        QueryOptions options = new QueryOptions();
        Map<String, QueryOptions.Ordering> order = new HashMap<>();
        order.put("certificate.name", QueryOptions.Ordering.DESC);
        options.setOrder(order);

        List<BaseSqlSpecification<Certificate>> specifications = new ArrayList<>();
        specifications.add(new CertificateByTagNameSpecification("tag2"));
        specifications.add(new CertificateLikeNameSpecification("erti"));
        BaseSqlSpecification<Certificate> andSpecification = new AndSpecification<>(specifications);

        List<Certificate> certificates = certificateRepository.queryForList(andSpecification, options);

        assertEquals(2, certificates.size());

        Certificate founded = certificates.get(0);

        assertEquals("certificate5", founded.getName());
    }

    @Test
    void testShouldReturnUpdatedCertificate() {
        Certificate certificate = certificateRepository.queryForOne(new CertificateFindByIdSpecification(4L)).get();
        certificate.setName("update");
        Certificate updated = certificateRepository.update(certificate);

        assertEquals("update", updated.getName());
    }

}
