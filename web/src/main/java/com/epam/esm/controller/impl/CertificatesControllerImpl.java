package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
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
    public ResponseEntity<CertificateDTO> add(CertificateDTO certificateDTO) {
        certificateService.add(certificateDTO);
        URI location = URI.create(String.format("/certificates/%s", certificateDTO.getId()));
        return ResponseEntity.created(location).body(certificateService.findById(certificateDTO.getId()));
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    // пока не работает ..
    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        certificateService.update(certificateDTO);
        return certificateDTO;
    }

    // добавляет, только если такой тэг существует
    // Post Put??
    //todo: добавить TagNameDTO или List<String> tags
    @Override
    public String addTag(Long id, TagDTO tagDTO) {
        certificateService.addTagToCertificate(id, tagDTO);
        return "Tag was added to certificate";
    }
//    @PatchMapping("/certificates/{id}")
//    @ResponseStatus(HttpStatus.OK) // ok?
//    public String addTagToCertificate(@PathVariable long id, @RequestBody Tag tag) {
//        certificateService.addTagToCertificate(id, tag);
//        return "Tag was added to certificate";
//    }
}
