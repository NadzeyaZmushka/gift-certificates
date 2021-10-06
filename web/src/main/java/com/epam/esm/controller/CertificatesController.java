package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificatesController {

    private final CertificateService certificateService;

    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public List<Certificate> findAll() {
        return certificateService.findAll();
    }

    @GetMapping("certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate findOne(@PathVariable long id) {
        return certificateService.findById(id);
    }

    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Certificate> add(@RequestBody Certificate certificate) {
        certificateService.add(certificate);
        URI location = URI.create(String.format("/certificates/%s", certificate.getId()));
        return ResponseEntity.created(location).body(certificateService.findById(certificate.getId()));
    }

    //todo: ничего не возвращать?
    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable long id) {
        certificateService.delete(id);
        return "Certificate with id = " + id + " was deleted";
    }

    // пока не работает ..
    @PutMapping("/certificates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Certificate update(@RequestBody Certificate certificate) {
        certificateService.update(certificate);
        return certificate;
    }

    // добавляет, только если такой тэг существует
    // Post Put??
    //todo: добавить TagNameDTO или List<String> tags
    @PutMapping("/certificates/{id}/tags")
    @ResponseStatus(HttpStatus.OK) // ok?
    public String addTag(@PathVariable long id, @RequestBody Tag tag) {
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
