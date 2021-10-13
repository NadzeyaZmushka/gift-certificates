package com.epam.esm.service.impl;

import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagAndCertificate;
import com.epam.esm.exception.ErrorConstants;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.impl.TagToCertificateRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.BaseSqlSpecification;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.OrSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByTagNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNamesSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.CERTIFICATE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private static final String TABLE = "certificate";

    private final CertificateRepository certificateRepository;
    private final TagRepositoryImpl tagRepository;
    private final TagToCertificateRepositoryImpl tagToCertificateRepository;
    private final CertificateConverter mapper;
//    private final TagDTOMapper tagDTOMapper;

    @Override
    public Certificate add(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.add(certificate);
    }

    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificateList = certificateRepository.queryForList(new FindAllSpecification(TABLE));
        for (Certificate certificate : certificateList) {
            certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));

        }
        return certificateList;
    }

    @Override
    public Certificate findById(long id) {
        Certificate certificate = certificateRepository.queryForOne(new FindByIdSpecification(TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + id
                        , CERTIFICATE_NOT_FOUND.getErrorCode()));
        certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));
        return certificate;
    }

    @Override
    public void delete(Long id) {
        Certificate certificate = findById(id);
        if (certificate == null) {
            throw new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + id
                    , CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        certificateRepository.remove(certificate);
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.update(certificate);

    }

    @Override
    public List<Certificate> findByCriteria(String tagName, String namePart, String orderBy) {
        BaseSqlSpecification<Certificate> specByTagName = new CertificateByTagNameSpecification(tagName);
        OrSpecification<Certificate> specification = new OrSpecification<>(specByTagName);
        return null;
    }

    @Override
    @Transactional
    public void addTagsToCertificate(Long certificateId, List<String> tagsNames) {
        SqlSpecification specification = new FindByIdSpecification(TABLE, certificateId);
        Certificate certificate = certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + certificateId, CERTIFICATE_NOT_FOUND.getErrorCode()));
        List<Tag> tags = tagRepository.queryForList(new TagFindByNamesSpecification(tagsNames));
        List<TagAndCertificate> tagCertificateList = tags
                .stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.addAll(tagCertificateList);
        certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + certificateId, CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

//    @Override
//    public void addTagsToCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO) {
//        SqlSpecification specification = new FindByIdSpecification("certificate", certificateId);
//        Certificate certificate = certificateRepository.queryForOne(specification)
//                .orElseThrow(() -> new NoSuchEntityException("Certificate was not found", CERTIFICATE_NOT_FOUND.getErrorCode()));
//        List<String> tags = tagToCertificateDTO.getTagNames();
//        tags.stream()
//                .distinct()
//                .forEach(tagName -> {
//                    Tag tag = tagRepository.queryForOne(new TagFindByNameSpecification(tagName)).orElseGet(() -> {
//                        Tag tag1 = new Tag();
//                        tag1.setName(tagName);
//                        return tagRepository.add(tag1);
//                    });
//                    tagToCertificateRepository.add(new TagAndCertificate(certificate.getId(), tag.getId()));
//                });
//    }

    @Override
    public void deleteTagFromCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO) {
        SqlSpecification specification = new FindByIdSpecification(TABLE, certificateId);
        Certificate certificate = certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + certificateId, CERTIFICATE_NOT_FOUND.getErrorCode()));
//        List<String> tags1 = tagToCertificateDTO.getTagNames();
//        tags1.stream().distinct().forEach(tagName -> {
//            Tag tag = tagRepository.queryForOne(new TagFindByNameSpecification(tagName)).orElseThrow(() -> new NoSuchEntityException("Tag was not found", TAG_NOT_FOUND.getErrorCode()));
//            tagToCertificateRepository.remove(new TagAndCertificate(certificate.getId(), tag.getId()));
//        });
        List<Tag> tags = tagRepository.queryForList(new TagFindByNamesSpecification(tagToCertificateDTO.getTagNames()));
        List<TagAndCertificate> tagCertificateList = tags.stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.removeAll(tagCertificateList);
    }

}
