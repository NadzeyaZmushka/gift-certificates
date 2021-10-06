package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
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

import java.util.List;

@RequestMapping("/api/certificates")
public interface CertificateController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Certificate> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Certificate findOne(@PathVariable Long id);

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Certificate> add(@RequestBody Certificate certificate);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @PutMapping("/certificates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Certificate update(@RequestBody Certificate certificate);

    @PutMapping("/certificates/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    String add(@PathVariable Long id, @RequestBody Tag tag);

}
