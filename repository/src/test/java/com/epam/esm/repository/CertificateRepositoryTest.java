package com.epam.esm.repository;

import com.epam.esm.config.TestRepositoryConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class CertificateRepositoryTest {

//    @Autowired
//    private BaseCrudRepository<Certificate> certificateRepository;
//
//    @Autowired
//    private BaseCrudRepository<Tag> tagRepository;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    CertificateMapper certificateMapper;
//
//    @Test
//    void testShouldReturnAllCertificates() {
//        List<Certificate> certificates = certificateRepository.findAll(, new FindAllSpecification<>("certificate"), );
//
//        assertEquals(5, certificates.size());
//
//        Certificate certificate1 = certificates.get(0);
//        certificate1.setTags(tagRepository.findAll(, new TagFindByCertificateIdSpecification(certificate1.getId()), ));
//
//        assertEquals(1L, certificate1.getId());
//        assertEquals("certificate1", certificate1.getName());
//
//        List<Tag> tags = certificate1.getTags();
//
//        assertEquals(2, tags.size());
//    }
//
//    @Test
//    void testShouldReturnCertificateWithSuchId() {
//        Optional<Certificate> certificate = certificateRepository.findOne(new FindByIdSpecification<>("certificate", 1L));
//        assertTrue(certificate.isPresent());
//        assertEquals(1L, certificate.get().getId());
//    }
//
//    @Test
//    void testShouldReturnAllCertificateByCriteria() {
//        QueryOptions options = new QueryOptions();
//        Map<String, QueryOptions.Ordering> order = new HashMap<>();
//        order.put("certificate.name", QueryOptions.Ordering.DESC);
//        options.setOrder(order);
//
//        List<BaseSqlSpecification<Certificate>> specifications = new ArrayList<>();
//        specifications.add(new CertificateByTagNameSpecification("tag2"));
//        specifications.add(new CertificateLikeNameSpecification("erti"));
//        BaseSqlSpecification<Certificate> andSpecification = new AndSpecification<>(specifications);
//
//        List<Certificate> certificates = certificateRepository.findAll(andSpecification, options);
//
//        assertEquals(2, certificates.size());
//
//        Certificate founded = certificates.get(0);
//
//        assertEquals("certificate5", founded.getName());
//    }
//
//    @Test
//    void testShouldReturnUpdatedCertificate() {
//        Certificate certificate = certificateRepository.findOne(new FindByIdSpecification<>("certificate",4L)).get();
//        certificate.setName("update");
//        Certificate updated = certificateRepository.update(certificate);
//
//        assertEquals("update", updated.getName());
//    }

}
