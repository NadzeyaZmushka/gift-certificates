package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagConvertor;
import com.epam.esm.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagServiceImpl tagService;
    private final TagConvertor mapper;

    @Override
    public List<TagDTO> findAll() {
        return tagService.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findOne(Long id) {
        return mapper.toDTO(tagService.findById(id));
    }

    @Override
    public void add(TagDTO tagDTO) {
        tagService.add(mapper.toEntity(tagDTO));
    }

    @Override
    public void delete(Long id) {
        tagService.delete(id);
    }

}
