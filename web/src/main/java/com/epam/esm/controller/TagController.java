package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/tags")
public interface TagController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TagDTO> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TagDTO findOne(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> add(@RequestBody TagDTO tagDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

}
