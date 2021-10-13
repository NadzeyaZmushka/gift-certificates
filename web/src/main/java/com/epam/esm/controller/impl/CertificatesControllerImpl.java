package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CertificatesControllerImpl implements CertificateController {

    private final CertificateServiceImpl certificateService;
    private final CertificateConverter mapper;

    @Override
    public List<CertificateDTO> findAll() {
        return certificateService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO findOne(Long id) {
        return mapper.toDTO(certificateService.findById(id));
    }

    @Override
    public List<Certificate> findAllByCriteria(String tagName, String namePart, String orderBy) {
        return certificateService.findByCriteria(tagName, namePart, orderBy);
    }

    @Override
    public void add(CertificateDTO certificateDTO) {
       certificateService.add(mapper.toEntity(certificateDTO));
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    @Override
    public void update(Long id, CertificateDTO certificateDTO) {
        certificateDTO.setId(id);
        certificateService.update(mapper.toEntity(certificateDTO));
    }

    // не добавялет
    @Override
    public void addTag(Long id, List<String> tagsNames) {
        certificateService.addTagsToCertificate(id, tagsNames);
    }

    @Override
    public void deleteTag(Long id, AddTagToCertificateDTO tags) {
        certificateService.deleteTagFromCertificate(id, tags);
    }

}
