package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.hateoas.CertificateLinkBuilder;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
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
    private final CertificateLinkBuilder hateoasLinkBuilder;

    @Override
    public PagedModel<CertificateDTO> findAll(List<String> tagNames, String partName, String sortBy, String order, int page, int limit) {
        List<CertificateDTO> certificateDTOList;
            certificateDTOList = certificateService.findAllByCriteria(tagNames, partName, sortBy, order, limit, page)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        certificateDTOList.forEach(hateoasLinkBuilder::addLinksForCertificate);
        Long count = certificateService.count();
        PagedModel<CertificateDTO> pagedModel = PagedModel.of(certificateDTOList, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel, tagNames, partName, sortBy, order);
        return pagedModel;
    }

    @Override
    public CertificateDTO findOne(Long id) {
        CertificateDTO dto = mapper.toDTO(certificateService.findById(id));
        hateoasLinkBuilder.addLinksForCertificate(dto);
        return dto;
    }

    @Override
    public ResponseEntity<Void> add(CertificateDTO certificateDTO) {
        Certificate certificate = certificateService.add(mapper.toEntity(certificateDTO));
        URI location = URI.create(String.format("/certificates/%d", certificate.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public CertificateDTO delete(Long id) {
        certificateService.delete(id);
        return null;
    }

    @Override
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        CertificateDTO dto = mapper.toDTO(certificateService.update(id, mapper.toEntity(certificateDTO)));
        hateoasLinkBuilder.addLinksForCertificate(dto);
        return dto;
    }

    @Override
    public void addTag(Long id, List<String> tagsNames) {
        certificateService.addTagsToCertificate(id, tagsNames);
    }

}
