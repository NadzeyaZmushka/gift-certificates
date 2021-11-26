package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.converter.TagConvertor;
import com.epam.esm.converter.WidelyUsedTagConverter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.TagsLinkBuilder;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.dto.WidelyUsedTagsDTO;
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
    private final TagConvertor converter;
    private final WidelyUsedTagConverter usedTagConverter;
    private final TagsLinkBuilder hateoasLinkBuilder;
    private final UserLinkBuilder userLinkBuilder;

    @Override
    public PagedModel<TagDTO> findAll(int page, int limit) {
        List<TagDTO> tags = tagService.findAll(limit, page).stream()
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
    public List<WidelyUsedTagsDTO> findWidelyUsed(int page, int limit) {
        List<WidelyUsedTagsDTO> tags = tagService.findWidelyUsed().stream()
                .map(usedTagConverter::toDTO)
                .collect(Collectors.toList());
        for (WidelyUsedTagsDTO tag : tags) {
            tag.getTags().forEach(hateoasLinkBuilder::addLinksForTag);
        }
        return tags;
    }

}
