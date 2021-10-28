package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CertificatesControllerImpl implements CertificateController {

    private final CertificateServiceImpl certificateService;
    private final CertificateConverter mapper;

    @Override
    public List<CertificateDTO> findAll(String tagName, String partName, String sortBy, String order) {
        List<CertificateDTO> certificateDTOList;
        if (tagName == null && partName == null) {
            certificateDTOList = certificateService.findAll()
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            certificateDTOList = certificateService.findAllByCriteria(tagName, partName, sortBy, order)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        }
        return certificateDTOList;
    }

    @Override
    public CertificateDTO findOne(Long id) {
        return mapper.toDTO(certificateService.findById(id));
    }

    @Override
    public ResponseEntity<Void> add(CertificateDTO certificateDTO) {
        Certificate certificate = certificateService.add(mapper.toEntity(certificateDTO));
        URI location = URI.create(String.format("/certificates/%d", certificate.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    @Override
    public void update(Long id, CertificateDTO certificateDTO) {
//        certificateDTO.setId(id);
        certificateService.update(id, mapper.toEntity(certificateDTO));
    }

    @Override
    public void addTag(Long id, List<String> tagsNames) {
        certificateService.addTagsToCertificate(id, tagsNames);
    }

    @Override
    public void deleteTag(Long id, List<String> tagNames) {
        certificateService.deleteTagFromCertificate(id, tagNames);
    }

}
