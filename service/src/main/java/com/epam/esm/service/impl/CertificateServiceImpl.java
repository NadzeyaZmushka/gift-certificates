package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagAndCertificate;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.QueryOptions;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.BaseSqlSpecification;
import com.epam.esm.specification.impl.AndSpecification;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByNameSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByOrderIdSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByTagNameSpecification;
import com.epam.esm.specification.impl.certificate.CertificateLikeNameSpecification;
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
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_WITH_NAME_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final BaseCrudRepository<Certificate> certificateRepository;
    private final CrudRepository<TagAndCertificate> tagToCertificateRepository;
    private final Translator translator;
    private final CertificateValidator validator;
    private final TagService tagService;

    private static final String CERTIFICATE_TABLE = "certificate";

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
        certificateList = certificateRepository.queryForList(new FindAllSpecification<>(CERTIFICATE_TABLE));
        receiveTagsForCertificates(certificateList);

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
        receiveTagsForCertificates(certificateList);

        return certificateList;
    }

    @Override
    public Certificate findById(Long id) {
        Certificate certificate = certificateRepository.queryForOne(new FindByIdSpecification<>(CERTIFICATE_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_ID_NOT_FOUND), id)
                        , CERTIFICATE_NOT_FOUND.getErrorCode()));
        certificate.setTags(tagService.findByCertificateId(certificate.getId()));

        return certificate;
    }

    @Override
    public boolean delete(Long id) {
        Certificate certificate = findById(id);
        if (certificate == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_ID_NOT_FOUND), id)
                    , CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return certificateRepository.remove(certificate);
    }

//    @Override
//    public Certificate update(Certificate certificate) {
//        validator.validCertificate(certificate);
//        certificate.setLastUpdateDate(LocalDateTime.now());
//        return certificateRepository.update(certificate);
//
//    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        Certificate fromDB = findById(id);
        fromDB.setName(certificate.getName() == null ? fromDB.getName() : certificate.getName());
        fromDB.setDescription(certificate.getDescription() == null ? fromDB.getDescription() : certificate.getDescription());
        fromDB.setPrice(certificate.getPrice() == null ? fromDB.getPrice() : certificate.getPrice());
        fromDB.setDuration((certificate.getDuration() == null ? fromDB.getDuration() : certificate.getDuration()));
        fromDB.getTags().addAll(certificate.getTags());
        fromDB.setCreateDate(fromDB.getCreateDate());
        validator.validCertificate(fromDB);
        fromDB.setLastUpdateDate(LocalDateTime.now());
        return certificateRepository.update(fromDB);

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
        List<TagAndCertificate> tagCertificateList = tagsToAdd.stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.addAll(tagCertificateList);
    }

    @Override
    @Transactional
    public void deleteTagFromCertificate(Long certificateId, List<String> tagsNames) {
        Certificate certificate = findById(certificateId);
        List<Tag> tags = tagService.findByNames(tagsNames);
        List<TagAndCertificate> tagCertificateList = tags.stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.removeAll(tagCertificateList);
    }

    @Override
    public Certificate findByName(String name) {
        return certificateRepository.queryForOne(new CertificateByNameSpecification(name))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(CERTIFICATE_WITH_NAME_NOT_FOUND), name),
                        CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    //todo message and error code
    @Override
    public Certificate findByOrderId(Long orderId) {
        return certificateRepository.queryForOne(new CertificateByOrderIdSpecification(orderId))
                .orElseThrow(() -> new NoSuchEntityException("", 1));
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

    private void receiveTagsForCertificates(List<Certificate> certificates) {
        for (Certificate certificate : certificates) {
            certificate.setTags(tagService.findByCertificateId(certificate.getId()));
        }
    }

}
