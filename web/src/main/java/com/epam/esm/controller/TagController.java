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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Interface to perform REST's CRUD operations with tags
 *
 * @author Nadzeya Zmushka
 */
@RequestMapping("/api/tags")
public interface TagController {

    /**
     * Realizes REST's read operation of resource
     *
     * @return list of tag TagDTOs in JSON format
     * @param page
     * @param limit
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<TagDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                         @RequestParam(required = false, name = "limit", defaultValue = "1000") int limit
                         );

    /**
     * Realizes REST's read operation a resource with id in a request path
     *
     * @param id of a requested resource
     * @return TagDTO with the specified id if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TagDTO findOne(@PathVariable Long id);

    /**
     * Realizes REST's create operation
     *
     * @param tagDTO tagDTO with all information
     * @return response with status OK if tag was created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> add(@RequestBody TagDTO tagDTO);

    /**
     * Realizes REST's delete operation of a resource
     *
     * @param id of tag to be deleted
     * @return null
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    TagDTO delete(@PathVariable Long id);

    @GetMapping("/widelyUsed")
    @ResponseStatus(HttpStatus.OK)
    TagDTO findWidelyUsed();

}
