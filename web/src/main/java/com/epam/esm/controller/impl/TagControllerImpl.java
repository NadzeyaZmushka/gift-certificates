package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagServiceImpl tagService;

    @Override
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @Override
    public Tag findOne(Long id) {
        return tagService.findById(id);
    }

    @Override
    public ResponseEntity<Tag> add(Tag tag) {
        tagService.add(tag);
        URI location = URI.create(String.format("/tags/%s", tag.getId()));
        return ResponseEntity.created(location).body(tagService.findById(tag.getId()));
    }

    @Override
    public void delete(Long id) {
        tagService.delete(id);
    }

}