package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.specification.impl.certificate.CertificateFindAllSpecification;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private BaseCrudRepository<Certificate> certificateRepository;
    @Mock
    private BaseCrudRepository<Tag> tagRepository;
    @Mock
    private CertificateValidator validator;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private Translator translator;
    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Test
    void testShouldAddCertificate() {
        //given
        Certificate certificate = new Certificate("name", "description", new BigDecimal(100), 10,
                LocalDateTime.now(), LocalDateTime.now(),
                Arrays.asList(new Tag(1L, "tag"), new Tag(2L, "tag2")));
        when(certificateRepository.add(certificate)).thenReturn(new Certificate(1L, "name", "description",
                new BigDecimal(100), 10,
                LocalDateTime.now(), LocalDateTime.now()));
        //when
        Certificate actual = certificateService.add(certificate);
        //then
        assertEquals(certificate.getName(), actual.getName());
        assertNotNull(actual.getId());
    }

    @Test
    void testFindAllWithoutResult() {
        //given
        when(certificateRepository.queryForList(any(CertificateFindAllSpecification.class))).thenReturn(Collections.emptyList());
        //when
        List<Certificate> actual = certificateService.findAll();
        //then
        assertEquals(Collections.emptyList(), actual);
    }

//    @Test
//    void testShouldDeleteCertificate() {
//        Certificate certificate = new Certificate();
//        List<Tag> tags = new ArrayList<>();
//        when(tagRepository.queryForList(any(TagFindByCertificateIdSpecification.class)))
//                .thenReturn(tags);
//        certificate.setTags(tags);
//        when(certificateRepository.queryForOne(any(CertificateFindByIdSpecification.class)))
//                .thenReturn(Optional.of(certificate));
//        certificateService.delete(1L);
//        verify(certificateRepository, times(1)).remove(certificate);
//    }
//
//    // ???
//    @Test
//    void findAllByCriteria() {
//        Certificate certificate = new Certificate(1L, "name", "description", new BigDecimal(100),
//                10, LocalDateTime.now(), LocalDateTime.now(),
//                Arrays.asList(new Tag(1L, "tag"), new Tag(2L, "tag2")));
//        List<Certificate> certificates = List.of(certificate);
//        when(certificateRepository.queryForList(any(AndSpecification.class),any(QueryOptions.class))).thenReturn(certificates);
//
//        List<Certificate> actual = certificateService.findAllByCriteria("tag", "am", "name", "asc");
//
//        assertEquals(certificates, actual);
//    }

}