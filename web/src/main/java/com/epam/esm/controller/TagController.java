package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> showAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getTag(@PathVariable long id) {
        return tagService.findById(id);
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tag> addNewTag(@RequestBody Tag tag) {
        tagService.add(tag);
        URI location = URI.create(String.format("/tags/%s", tag.getId()));
        return ResponseEntity.created(location).body(tagService.findById(tag.getId()));
    }

    //todo: ничего не возвращать?
    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable long id) {
        tagService.delete(id);
        return "Tag with id = " + id + " was deleted";
    }

}
