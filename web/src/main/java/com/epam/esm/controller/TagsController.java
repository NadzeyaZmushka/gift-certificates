package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchTagException;
import com.epam.esm.exception.TagIncorrectData;
import com.epam.esm.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagsController {

    private final TagService tagService;

    @Autowired
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<Tag> showAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/tags/{id}")
    public Tag getTag(@PathVariable int id) {
        Tag tag = tagService.findById(id);

        if (tag == null) {
            throw new NoSuchTagException("No tag with id = " + id);
        }
        return tag;
    }

}
