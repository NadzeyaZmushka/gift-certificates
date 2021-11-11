package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateRepositoryImpl certificateRepository;
    @Mock
    private TagRepositoryImpl tagRepository;
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private CertificateValidator validator;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private Translator translator;
    @InjectMocks
    private CertificateServiceImpl certificateService;

    //
    @Test
    void testShouldAddCertificate() {
        //given
        Certificate certificate = new Certificate(1L, "name", "description", new BigDecimal(100), 10,
                LocalDateTime.now(), LocalDateTime.now(),
                new ArrayList<>());
        when(certificateRepository.add(certificate)).thenReturn(new Certificate(1L, "name", "description",
                new BigDecimal(100), 10,
                LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>()));
        //when
        Certificate actual = certificateService.add(certificate);
        //then
        assertEquals(certificate.getName(), actual.getName());
        assertNotNull(actual.getId());
    }

//    @Test
//    void testShouldReturnCertificateWithSuchId() {
//        //given
//        Certificate certificate = new Certificate(1L, "name", "description",
//                new BigDecimal(100), 10, LocalDateTime.now(), LocalDateTime.now(),
//                new ArrayList<>());
//        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
//        when(tagRepository.findByCertificateId(1L)).thenReturn(new ArrayList<>());
//        //when
//        Certificate actual = certificateService.findById(1L);
//        List<Tag> tags = tagService.findByCertificateId(1L);
//        actual.setTags(tags);
//        //then
//        assertEquals(certificate.getName(), actual.getName());
//    }

    @Test
    void testShouldReturnAllCertificates() {
        //given
        List<Certificate> certificates = List.of(new Certificate());
        when(certificateRepository.findAll(1, 10)).thenReturn(certificates);
        //when
        List<Certificate> actual = certificateService.findAll(10, 1);
        //then
        assertEquals(certificates.size(), actual.size());
        assertEquals(certificates, actual);
    }

    @Test
    void testReturnAllWithoutResult() {
        //given
        when(certificateRepository.findAll(1, 10)).thenReturn(Collections.emptyList());
        //when
        List<Certificate> actual = certificateService.findAll(10, 1);
        //then
        assertEquals(Collections.emptyList(), actual);
    }

}