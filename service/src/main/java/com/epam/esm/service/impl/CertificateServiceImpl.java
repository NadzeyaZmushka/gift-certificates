package com.epam.esm.service.impl;

import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.mapper.CertificateDTOMapper;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagAndCertificate;
import com.epam.esm.exception.ErrorConstants;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.impl.TagToCertificateRepositoryImpl;
import com.epam.esm.repository.specification.ByCriteriaSpecification;
import com.epam.esm.repository.specification.SqlSpecification;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import com.epam.esm.repository.specification.impl.TagByCertificateIdSpecification;
import com.epam.esm.repository.specification.impl.TagFindByNamesSpecification;
import com.epam.esm.service.CertificateService;
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

    private final CertificateRepositoryImpl certificateRepository;
    private final TagRepositoryImpl tagRepository;
    private final TagToCertificateRepositoryImpl tagToCertificateRepository;
    private final CertificateDTOMapper mapper;
//    private final TagDTOMapper tagDTOMapper;

    @Override
    public CertificateDTO add(CertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(LocalDateTime.now());
        certificateDTO.setLastUpdateDate(LocalDateTime.now());
        return mapper.toDTO(certificateRepository.add(mapper.toEntity(certificateDTO)));
    }

    @Override
    public List<CertificateDTO> findAll() {
        List<CertificateDTO> certificateDTOList = certificateRepository.queryForList(new FindAllSpecification(TABLE))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        for (CertificateDTO certificateDTO : certificateDTOList) {
            certificateDTO.setTags(tagRepository.queryForList(new TagByCertificateIdSpecification(certificateDTO.getId()))
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList()));

        }
        return certificateDTOList;
    }

    @Override
    public CertificateDTO findById(long id) {
        CertificateDTO certificateDTO = mapper.toDTO(certificateRepository.queryForOne(new FindByIdSpecification(TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + id
                        , CERTIFICATE_NOT_FOUND.getErrorCode())));
        certificateDTO.setTags(tagRepository.queryForList(new TagByCertificateIdSpecification(certificateDTO.getId()))
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
        return certificateDTO;
    }

    @Override
    public void delete(Long id) {
        CertificateDTO certificateDTO = findById(id);
        if (certificateDTO == null) {
            throw new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + id
                    , CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        Certificate certificate = mapper.toEntity(certificateDTO);
        certificateRepository.remove(certificate);
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        CertificateDTO certificate = findById(certificateDTO.getId());
        certificate.setName(certificateDTO.getName());
        certificate.setDescription(certificateDTO.getDescription());
        certificate.setPrice(certificateDTO.getPrice());
        certificate.setDuration(certificateDTO.getDuration());
        certificate.setCreateDate(certificateDTO.getCreateDate());
        certificate.setLastUpdateDate(LocalDateTime.now());
        return mapper.toDTO(certificateRepository.update(mapper.toEntity(certificate)));

    }

    @Override
    public List<CertificateDTO> findByCriteria(String tagName, String namePart, String orderBy) {
        ByCriteriaSpecification specification = new ByCriteriaSpecification(tagName, namePart, orderBy);

        //todo:

        return certificateRepository.queryForList(specification)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addTagsToCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO) {
        SqlSpecification specification = new FindByIdSpecification(TABLE, certificateId);
        Certificate certificate = certificateRepository.queryForOne(specification)
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_CERTIFICATE_WITH_ID + certificateId, CERTIFICATE_NOT_FOUND.getErrorCode()));
        List<Tag> tags = tagRepository.queryForList(new TagFindByNamesSpecification(tagToCertificateDTO.getTagNames()));
        List<TagAndCertificate> tagCertificateList = tags
                .stream()
                .map(tag -> new TagAndCertificate(certificate.getId(), tag.getId()))
                .collect(Collectors.toList());
        tagToCertificateRepository.addAll(tagCertificateList);
        certificateRepository.queryForOne(specification)
                .map(mapper::toDTO)
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
