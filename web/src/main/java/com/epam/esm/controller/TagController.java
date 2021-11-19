package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import org.springframework.hateoas.PagedModel;
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

import javax.validation.Valid;

@RequestMapping("/api/tags")
public interface TagController {

    /**
     * Realizes REST's read operation of resource
     *
     * @param page  page
     * @param limit limit
     * @return list of tag TagDTOs in JSON format
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedModel<TagDTO> findAll(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                               @RequestParam(required = false, name = "limit", defaultValue = "10") int limit
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
    ResponseEntity<Void> create(@RequestBody @Valid TagDTO tagDTO);

    /**
     * Realizes REST's delete operation of a resource
     *
     * @param id of tag to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping("/most-used")
    @ResponseStatus(HttpStatus.OK)
    PagedModel<TagDTO> findWidelyUsed(@RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                      @RequestParam(required = false, name = "limit", defaultValue = "10") int limit);

}

