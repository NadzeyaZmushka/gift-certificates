package com.epam.esm.controller;

import com.epam.esm.dto.AddTagToCertificateDTO;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CertificateDTO> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CertificateDTO findOne(@PathVariable Long id);

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    List<CertificateDTO> findAllByCriteria(@RequestParam(name = "tagName", required = false) String tagName,
                                           @RequestParam(name = "namePart", required = false) String namePart,
                                           @RequestParam(name = "orderBy", required = false, defaultValue = "id") String orderBy);

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CertificateDTO> add(@RequestBody CertificateDTO certificateDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    CertificateDTO update(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO);

    @PostMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    String addTag(@PathVariable Long id, @RequestBody AddTagToCertificateDTO tags);

    @DeleteMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTag(@PathVariable Long id, @RequestBody AddTagToCertificateDTO tags);

}
