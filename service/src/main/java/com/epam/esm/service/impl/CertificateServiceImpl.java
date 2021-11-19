package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.CustomErrorCode.PAGE_INCORRECT_CODE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_NAME_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.PAGE_INCORRECT;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final Translator translator;
    private final CertificateValidator validator;
    private final TagService tagService;

    @Override
    @Transactional
    public Certificate add(Certificate certificate) {
        validator.validCertificate(certificate);
        List<Tag> tagsToAdd = getCertificateTagNames(certificate).stream()
                .map(tagService::findByNameOrCreate)
                .collect(Collectors.toList());
        certificate.setTags(tagsToAdd);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());

        return certificateRepository.add(certificate);
    }

    @Override
    public List<Certificate> findAll(int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
        }
        return certificateRepository.findAll(page, limit);
    }

    @Override
    public List<Certificate> findAllByCriteria(List<String> tagNames, String partName, String sortBy, String order, int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
        }
        return certificateRepository.findAll(tagNames, partName, sortBy, order, page, limit);
    }

    @Override
    public Certificate findById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_ID_NOT_FOUND), id),
                        CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Certificate certificate = findById(id);
        certificateRepository.remove(certificate);
    }

    @Override
    public Long count() {
        return certificateRepository.count();
    }

    @Override
    @Transactional
    public Certificate update(Long id, Certificate certificate) {
        Certificate fromDB = findById(id);
        fromDB.setName(ofNullable(certificate.getName()).orElse(fromDB.getName()));
        fromDB.setDescription(ofNullable(certificate.getDescription()).orElse(fromDB.getDescription()));
        fromDB.setPrice(ofNullable(certificate.getPrice()).orElse(fromDB.getPrice()));
        fromDB.setDuration(ofNullable(certificate.getDuration()).orElse(fromDB.getDuration()));
        fromDB.setCreateDate(fromDB.getCreateDate());
        setUpdatedTags(fromDB, certificate.getTags());
        validator.validCertificate(fromDB);
        fromDB.setLastUpdateDate(LocalDateTime.now());
        certificateRepository.update(fromDB);

        return fromDB;
    }

    @Override
    @Transactional
    public void addTagsToCertificate(Long certificateId, List<String> tagsNames) {
        if (tagsNames.isEmpty()) {
            return;
        }
        Certificate certificate = findById(certificateId);
        List<Tag> tagsToAdd = getTagsToAdd(certificate.getTags(), tagsNames).stream()
                .map(tagService::findByNameOrCreate)
                .collect(Collectors.toList());
        certificate.getTags().addAll(tagsToAdd);
        certificate.setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    public Certificate findByName(String name) {
        return certificateRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_NAME_NOT_FOUND), name)
                        , CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    private List<String> getTagsToAdd(List<Tag> tags, List<String> tagNames) {
        List<String> certificatesTag = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return tagNames.stream()
                .distinct()
                .filter(name -> !certificatesTag.contains(name))
                .collect(Collectors.toList());
    }

    private List<String> getCertificateTagNames(Certificate certificate) {
        return certificate.getTags().stream()
                .distinct()
                .map(Tag::getName)
                .collect(Collectors.toList());

    }

    private void setUpdatedTags(Certificate certificateFromDb, List<Tag> tags) {
        if (tags.isEmpty()) {
            return;
        }
        List<Tag> updatedTags = tags.stream()
                .distinct()
                .map(tag -> tagService.findByNameOrCreate(tag.getName()))
                .collect(Collectors.toList());
        certificateFromDb.setTags(updatedTags);
    }

}
