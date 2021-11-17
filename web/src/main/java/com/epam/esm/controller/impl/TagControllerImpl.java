package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.converter.TagConvertor;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.TagsLinkBuilder;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagServiceImpl tagService;
    private final TagConvertor converter;
    private final TagsLinkBuilder hateoasLinkBuilder;

    @Override
    public PagedModel<TagDTO> findAll(int page, int limit) {
        List<TagDTO> tags = tagService.findAll(limit, page)
                .stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        tags.forEach(hateoasLinkBuilder::addLinksForTag);
        Long count = tagService.count();
        PagedModel<TagDTO> pagedModel = PagedModel.of(tags, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel);
        return pagedModel;
    }

    @Override
    public TagDTO findOne(Long id) {
        TagDTO tagDTO = converter.toDTO(tagService.findById(id));
        hateoasLinkBuilder.addLinksForTag(tagDTO);
        return tagDTO;
    }

    @Override
    public ResponseEntity<Void> create(TagDTO tagDTO) {
        Tag tag = tagService.add(converter.toEntity(tagDTO));
        URI location = URI.create(String.format("/tags/%d", tag.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public void delete(Long id) {
        tagService.delete(id);
    }

    @Override
    public TagDTO findWidelyUsed() {
        TagDTO tagDTO = converter.toDTO(tagService.findWidelyUsed());
        Link linkForSelf = linkTo(methodOn(TagControllerImpl.class)
                .findWidelyUsed())
                .withSelfRel();
        Link findAll = linkTo(methodOn(TagControllerImpl.class)
                .findAll(1, 10))
                .withRel("findAll");
        tagDTO.add(linkForSelf, findAll);

        return tagDTO;
    }

}
