package com.epam.esm.service.impl;

import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.specification.SqlSpecification;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagRepositoryImpl tagRepository;
    private TagServiceImpl tagService;
    private List<Tag> testTags;
    private Tag testTag;

    @BeforeEach
    void setUp() {
        testTags = new ArrayList<>();
        testTags.add(new Tag(1L,"one"));
        testTags.add(new Tag(2L,"two"));
        testTags.add(new Tag(3L,"three"));
        tagRepository = mock(TagRepositoryImpl.class);
        tagService = mock(TagServiceImpl.class);
    }

    @Test
    void add() {
    }

    @Test
    void findAll() {
        SqlSpecification specification = new FindAllSpecification("tag");
        TagDTOMapper mapper = new TagDTOMapper(new ModelMapper());
        when(tagRepository.queryForList(specification)).thenReturn(testTags);
        List<Tag> actual = tagService.findAll().stream().map(mapper::toEntity).collect(Collectors.toList());
        assertEquals(testTags, actual);
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByName() {
    }
}