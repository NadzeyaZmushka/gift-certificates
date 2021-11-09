package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.CertificateRepository;
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
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_NAME_NOT_FOUND;

@Slf4j
@Service
@Transactional
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
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());

        return certificateRepository.add(certificate);
    }

    @Override
    @Transactional
    public List<Certificate> findAll(int limit, int page) {
        return certificateRepository.findAll(page, limit);
    }

    @Override
    @Transactional
    public List<Certificate> findAllByCriteria(List<String> tagNames, String partName, String sortBy, String order, int limit, int page) {
        return certificateRepository.findAll(tagNames, partName, sortBy, page, limit);
    }

    @Override
    @Transactional
    public Certificate findById(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_ID_NOT_FOUND), id)
                        , CERTIFICATE_NOT_FOUND.getErrorCode()));
        certificate.setTags(tagService.findByCertificateId(certificate.getId()));

        return certificate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Certificate certificate = findById(id);
        if (certificate == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_ID_NOT_FOUND), id)
                    , CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        certificateRepository.remove(certificate);
    }

    @Override
    @Transactional
    public Certificate update(Long id, Certificate certificate) {
        Certificate fromDB = findById(id);
        fromDB.setName(certificate.getName() == null ? fromDB.getName() : certificate.getName());
        fromDB.setDescription(certificate.getDescription() == null ? fromDB.getDescription() : certificate.getDescription());
        fromDB.setPrice(certificate.getPrice() == null ? fromDB.getPrice() : certificate.getPrice());
        fromDB.setDuration((certificate.getDuration() == null ? fromDB.getDuration() : certificate.getDuration()));
        fromDB.setCreateDate(fromDB.getCreateDate());
        //?
        fromDB.setTags(certificate.getTags());
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

    //todo: переделать, удаляет все теги у сертификата
    @Override
    @Transactional
    public void deleteTagFromCertificate(Long certificateId, List<String> tagsNames) {
        Certificate certificate = findById(certificateId);
        List<Tag> certificateTags = certificate.getTags();
        List<Tag> tags = tagService.findByNames(tagsNames);
        tags.forEach(certificateTags::remove);
        certificateRepository.update(certificate);
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

}
