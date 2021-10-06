package com.epam.esm.controller.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
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
    public List<Certificate> findAll() {
        return certificateService.findAll();
    }

    @Override
    public Certificate findOne(Long id) {
        return certificateService.findById(id);
    }

    @Override
    public ResponseEntity<Certificate> add(Certificate certificate) {
        certificateService.add(certificate);
        URI location = URI.create(String.format("/certificates/%s", certificate.getId()));
        return ResponseEntity.created(location).body(certificateService.findById(certificate.getId()));
    }

    @Override
    public void delete(Long id) {
        certificateService.delete(id);
    }

    // пока не работает ..
    @Override
    public Certificate update(Certificate certificate) {
        certificateService.update(certificate);
        return certificate;
    }

    // добавляет, только если такой тэг существует
    // Post Put??
    //todo: добавить TagNameDTO или List<String> tags
    @Override
    public String add(Long id, Tag tag) {
        certificateService.addTagToCertificate(id, tag);
        return "Tag was added to certificate";
    }
//    @PatchMapping("/certificates/{id}")
//    @ResponseStatus(HttpStatus.OK) // ok?
//    public String addTagToCertificate(@PathVariable long id, @RequestBody Tag tag) {
//        certificateService.addTagToCertificate(id, tag);
//        return "Tag was added to certificate";
//    }
}
