package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificatesControllerImpl implements CertificateController {

    private final CertificateServiceImpl certificateService;

    @Override
    public List<CertificateDTO> findAll() {
        return certificateService.findAll();
    }

    @Override
    public CertificateDTO findOne(Long id) {
        return certificateService.findById(id);
    }

    @Override
    public List<CertificateDTO> findAllByCriteria(String tagName, String namePart, String orderBy) {
        return certificateService.findByCriteria(tagName, namePart, orderBy);
    }

    @Override
    public ResponseEntity<CertificateDTO> add(CertificateDTO certificateDTO) {
        CertificateDTO newCertificate = certificateService.add(certificateDTO);
        URI location = URI.create(String.format("/certificates/%d", newCertificate.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    @Override
    public CertificateDTO update(Long id, CertificateDTO certificateDTO) {
        certificateDTO.setId(id);
        certificateService.update(certificateDTO);
        return certificateDTO;
    }

    // не добавялет
    @Override
    public String addTag(Long id, AddTagToCertificateDTO tags) {
        certificateService.addTagsToCertificate(id, tags);
        return "Tag was added to certificate";
    }

    @Override
    public void deleteTag(Long id, AddTagToCertificateDTO tags) {
        certificateService.deleteTagFromCertificate(id, tags);
    }

}
