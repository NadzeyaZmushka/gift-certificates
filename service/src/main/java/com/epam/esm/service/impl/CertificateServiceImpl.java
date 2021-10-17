package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagAndCertificate;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.QueryOptions;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.impl.TagToCertificateRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.BaseSqlSpecification;
import com.epam.esm.specification.impl.AndSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByTagNameSpecification;
import com.epam.esm.specification.impl.certificate.CertificateFindAllSpecification;
import com.epam.esm.specification.impl.certificate.CertificateFindByIdSpecification;
import com.epam.esm.specification.impl.certificate.CertificateLikeNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNamesSpecification;
import com.epam.esm.validator.CertificateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.CERTIFICATE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepositoryImpl tagRepository;
    private final TagToCertificateRepositoryImpl tagToCertificateRepository;
    private final Translator translator;
    private final CertificateValidator validator;

    @Override
    @Transactional
    public Certificate add(Certificate certificate) {
        validator.validCertificate(certificate);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.add(certificate);
    }

    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificateList;
        certificateList = certificateRepository.queryForList(new CertificateFindAllSpecification());
        for (Certificate certificate : certificateList) {
            certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));
        }
        return certificateList;
    }

    @Override
    public List<Certificate> findAllByCriteria(String tagName, String partName, String sortBy, String order) {
        QueryOptions options = new QueryOptions();
        Map<String, QueryOptions.Ordering> orderingMap = new HashMap<>();
        orderingMap.put(sortBy, QueryOptions.Ordering.valueOf(order.toUpperCase()));
        options.setOrder(orderingMap);

        List<BaseSqlSpecification<Certificate>> specifications = new ArrayList<>();
        Optional.ofNullable(tagName)
                .map(CertificateByTagNameSpecification::new)
                .ifPresent(specifications::add);

        Optional.ofNullable(partName)
                .map(CertificateLikeNameSpecification::new)
                .ifPresent(specifications::add);
        AndSpecification<Certificate> specification = new AndSpecification<>(specifications);

        List<Certificate> certificateList = certificateRepository.queryForList(specification, options);

        for (Certificate certificate : certificateList) {
            certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));
        }
        return certificateList;
    }

    @Override
    public Certificate findById(Long id) {
        Certificate certificate = certificateRepository.queryForOne(new CertificateFindByIdSpecification(id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("certificate.withIdNotFound"), id)
                        , CERTIFICATE_NOT_FOUND.getErrorCode()));
        certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));
        return certificate;
    }

    @Override
    public boolean delete(Long id) {
        Certificate certificate = findById(id);
        if (certificate == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale("certificate.withIdNotFound"), id)
                    , CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return certificateRepository.remove(certificate);
    }

    @Override
    public Certificate update(Certificate certificate) {
        validator.validCertificate(certificate);
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.update(certificate);

    }

    @Override
    @Transactional
    public void addTagsToCertificate(Long certificateId, List<String> tagsNames) {
        CertificateFindByIdSpecification specification = new CertificateFindByIdSpecification(certificateId);
        Certificate certificate = certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("certificate.withIdNotFound"), certificateId),
                        CERTIFICATE_NOT_FOUND.getErrorCode()));
        certificate.setTags(tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificate.getId())));
        List<String> uniqueTags = getUniqueTags(certificate.getTags(), tagsNames);
        List<Tag> tags = tagRepository.queryForList(new TagFindByNamesSpecification(uniqueTags));
        List<TagAndCertificate> tagCertificateList = tags
                .stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.addAll(tagCertificateList);
        certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("certificate.withIdNotFound"), certificateId), CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public void deleteTagFromCertificate(Long certificateId, List<String> tagsNames) {
        CertificateFindByIdSpecification specification = new CertificateFindByIdSpecification(certificateId);
        Certificate certificate = certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("certificate.withIdNotFound"), certificateId),
                        CERTIFICATE_NOT_FOUND.getErrorCode()));
        List<Tag> tags = tagRepository.queryForList(new TagFindByNamesSpecification(tagsNames));
        List<TagAndCertificate> tagCertificateList = tags.stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.removeAll(tagCertificateList);
    }

    private List<String> getUniqueTags(List<Tag> tags, List<String> tagNames) {
        List<String> uniqueTags;
        List<String> certificatesTag = tags
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        uniqueTags = tagNames.stream()
                .distinct()
                .filter(name -> !certificatesTag.contains(name))
                .collect(Collectors.toList());
        uniqueTags.forEach(tagName ->
                tagRepository.queryForOne(new TagFindByNameSpecification(tagName)).orElseGet(() -> {
                    Tag tag = new Tag();
                    tag.setName(tagName);
                    return tagRepository.add(tag);
                })
        );
        return uniqueTags;
    }

}
