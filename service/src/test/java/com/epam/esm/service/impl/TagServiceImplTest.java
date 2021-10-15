package com.epam.esm.service.impl;

import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TagServiceImplTest {

    @Mock
    private TagRepositoryImpl tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
//    private List<Tag> testTags;
//    private Tag testTag;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add() {
//        Tag tag = new Tag("Test tag");
//        when(tagRepository.add(tag)).thenReturn(tag);
//
//        tagService.add(mapper.toDTO(tag));
//
//        verify(tagRepository, times(1)).add(tag);
    }

    @Test
    void findAll() {
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