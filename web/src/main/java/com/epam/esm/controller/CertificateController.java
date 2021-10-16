package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/certificates")
public interface CertificateController {

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    List<CertificateDTO> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CertificateDTO findOne(@PathVariable Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CertificateDTO> findAll(@RequestParam(required = false, name = "tagName") String tagName,
                                 @RequestParam(required = false, name = "partName") String partName,
                                 @RequestParam(required = false, defaultValue = "certificate.id", name = "sortBy") String sortBy,
                                 @RequestParam(required = false, defaultValue = "ASC", name = "order") String order);

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> add(@RequestBody CertificateDTO certificateDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO);

    @PostMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    void addTag(@PathVariable Long id, @RequestBody List<String> tagsNames);

    @DeleteMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTag(@PathVariable Long id, @RequestBody List<String> tagNames);

}
