package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.CertificateDTOMapper;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagToCertificateRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepositoryImpl certificateRepository;
    private final TagToCertificateRepositoryImpl tagToCertificateRepository;
    private final CertificateDTOMapper mapper;
    private final TagDTOMapper tagDTOMapper;

    @Override
    public CertificateDTO add(CertificateDTO certificateDTO) {
        return mapper.toDTO(certificateRepository.add(mapper.toEntity(certificateDTO)));
    }

    @Override
    public List<CertificateDTO> findAll() {
        return certificateRepository.queryForList(new FindAllSpecification("certificate"))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO findById(long id) {
        return mapper.toDTO(certificateRepository.queryForOne(new FindByIdSpecification("certificate", id))
                .orElseThrow(() -> new NoSuchEntityException("No certificate with id = " + id
                        , CustomErrorCode.CERTIFICATE_NOT_FOUND.getErrorCode())));
    }

    @Override
    public void delete(Long id) {
        CertificateDTO certificateDTO = findById(id);
        if (certificateDTO == null) {
            throw new NoSuchEntityException("There is no certificate with id = " + id
                    , CustomErrorCode.CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        Certificate certificate = mapper.toEntity(certificateDTO);
        certificateRepository.remove(certificate);
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        return mapper.toDTO(certificateRepository.update(mapper.toEntity(certificateDTO)));
    }

    //???
    @Override
    public void addTagToCertificate(Long certificateId, TagDTO tagDTO) {
        Tag tag = tagDTOMapper.toEntity(tagDTO);
        tagToCertificateRepository.addTagToCertificate(certificateId, tag);
    }

    @Override
    public void deleteTagFromCertificate(Long certificateId, TagDTO tagDTO) {
        Tag tag = tagDTOMapper.toEntity(tagDTO);
        tagToCertificateRepository.deleteTagFromCertificate(certificateId, tag);
    }

}
