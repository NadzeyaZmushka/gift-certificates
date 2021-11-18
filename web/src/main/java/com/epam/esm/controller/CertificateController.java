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

/**
 * Interface to perform REST's CRUD operations with certificates
 *
 * @author Nadzeya Zmushka
 */
@RequestMapping("/api/certificates")
public interface CertificateController {

    /**
     * Realizes REST's read operation of resource.
     * May finds by criteria(tag name or part of name)
     *
     * @param tagName  tag name
     * @param partName part of certificate name
     * @param sortBy   field by which to sort
     * @param order    in what order to sort
     * @return list of certificate DTO in JSON format
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CertificateDTO> findAll(@RequestParam(required = false, name = "tagName") String tagName,
                                 @RequestParam(required = false, name = "partName") String partName,
                                 @RequestParam(required = false, defaultValue = "id", name = "sortBy") String sortBy,
                                 @RequestParam(required = false, defaultValue = "ASC", name = "order") String order);

    /**
     * Realizes REST's read operation a resource with id in a request path
     *
     * @param id of requested resource
     * @return CertificateDTO with the specified id if it exists in JSON format
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CertificateDTO findOne(@PathVariable Long id);

    /**
     * Realizes REST's create operation
     *
     * @param certificateDTO CertificateDTO with all information
     * @return response with status OK if certificate was created
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> add(@RequestBody CertificateDTO certificateDTO);

    /**
     * Realizes REST's delete operation of a resource
     *
     * @param id of certificate to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    /**
     * Realizes REST's update operation of a resource
     *
     * @param id             of certificate to be updated
     * @param certificateDTO an object with new fields of a resource
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO);

    /**
     * Adds new or existing tags to the certificate
     *
     * @param id        of certificate to be updated
     * @param tagsNames list of tag names in JSON format
     */
    @PostMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    void addTag(@PathVariable Long id, @RequestBody List<String> tagsNames);

    /**
     * Deletes tags from the certificate
     *
     * @param id       of certificate to be updated
     * @param tagNames list of tag names in JSON format to be deleted
     */
    @DeleteMapping("/{id}/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTag(@PathVariable Long id, @RequestBody List<String> tagNames);

}
