package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.hateoas.CertificateLinkBuilder;
import com.epam.esm.hateoas.TagsLinkBuilder;
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
    private final CertificateConverter converter;
    private final CertificateLinkBuilder hateoasLinkBuilder;
    private final TagsLinkBuilder tagsLinkBuilder;

    @Override
    public PagedModel<CertificateDTO> findAll(List<String> tagNames, String name, String sortBy, String order, int page, int limit) {
        List<CertificateDTO> certificates;
        certificates = certificateService.findAllByCriteria(tagNames, name, sortBy, order, limit, page)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        certificates.forEach(hateoasLinkBuilder::addLinksForCertificate);
        for (CertificateDTO certificateDTO : certificates) {
            certificateDTO.getTags().forEach(tagsLinkBuilder::addLinksForTag);
        }
        Long count = certificateService.count(tagNames, name);
        PagedModel<CertificateDTO> pagedModel = PagedModel.of(certificates, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel, tagNames, name, sortBy, order);
        return pagedModel;
    }

    @Override
    public CertificateDTO findOne(Long id) {
        CertificateDTO dto = converter.toDTO(certificateService.findById(id));
        hateoasLinkBuilder.addLinksForCertificate(dto);
        dto.getTags().forEach(tagsLinkBuilder::addLinksForTag);
        return dto;
    }

    @Override
    public ResponseEntity<Void> create(CertificateDTO certificateDTO) {
        Certificate certificate = certificateService.add(converter.toEntity(certificateDTO));
        URI location = URI.create(String.format("/certificates/%d", certificate.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    @Override
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        CertificateDTO dto = converter.toDTO(certificateService.update(id, converter.toEntity(certificateDTO)));
        hateoasLinkBuilder.addLinksForCertificate(dto);
        return dto;
    }

    @Override
    public void addTag(Long id, List<String> tagsNames) {
        certificateService.addTagsToCertificate(id, tagsNames);
    }

}
