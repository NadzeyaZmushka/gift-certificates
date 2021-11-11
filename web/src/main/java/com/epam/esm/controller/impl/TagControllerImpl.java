package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.TagsLinkBuilder;
import com.epam.esm.mapper.TagConvertor;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagServiceImpl tagService;
    private final TagConvertor mapper;
    private final TagsLinkBuilder hateoasLinkBuilder;

    @Override
    public PagedModel<TagDTO> findAll(int page, int limit) {
        List<TagDTO> tagDTOList = tagService.findAll(limit, page)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        tagDTOList.forEach(hateoasLinkBuilder::addLinksForTag);
        Long count = tagService.count();
        PagedModel<TagDTO> pagedModel = PagedModel.of(tagDTOList, new PagedModel.PageMetadata(limit, page, count));
        hateoasLinkBuilder.createPaginationLinks(pagedModel);
        return pagedModel;
    }

    @Override
    public TagDTO findOne(Long id) {
        TagDTO tagDTO = mapper.toDTO(tagService.findById(id));
        hateoasLinkBuilder.addLinksForTag(tagDTO);
        return tagDTO;
    }

    @Override
    public ResponseEntity<Void> add(TagDTO tagDTO) {
        Tag tag = tagService.add(mapper.toEntity(tagDTO));
        URI location = URI.create(String.format("/tags/%d", tag.getId()));
        return ResponseEntity.created(location).build();
    }

    @Override
    public TagDTO delete(Long id) {
        tagService.delete(id);
        return null;
    }

    @Override
    public TagDTO findWidelyUsed() {
        TagDTO tagDTO = mapper.toDTO(tagService.findWidelyUsed());
        hateoasLinkBuilder.addLinksForTag(tagDTO);
        return tagDTO;
    }

}
