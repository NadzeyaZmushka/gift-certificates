package com.epam.esm.service.impl;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepositoryImpl tagRepository;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private Translator translator;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void testShouldAddTag() {
        //given
        Tag tag = new Tag(1L, "tag");
        Tag addTag = new Tag("name");
        when(tagRepository.add(addTag)).thenReturn(tag);
        //when
        Tag actual = tagService.add(addTag);
        //then
        assertEquals(tag.getName(), actual.getName());
        assertEquals(1L, actual.getId());
        assertNotNull(actual.getId());
    }

    @Test
    void testShouldReturnAllTags() {
        //given
        List<Tag> tags = Arrays.asList(new Tag(1L, "tag"), new Tag(2L, "tag2"));
        when(tagRepository.findAll(1, 10)).thenReturn(tags);
        //when
        List<Tag> actual = tagService.findAll(10, 1);
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnTagWithSuchId() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        //when
        Tag expectedTag = tagService.findById(1L);
        //then
        assertEquals(expectedTag, tag);
    }

    @Test
    void testThrowsNoSuchEntityException() {
        //given
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(translator.toLocale(any())).thenReturn("message");
        //then
        assertThrows(NoSuchEntityException.class, () -> tagService.findById(1L));
    }

    @Test
    void testShouldDeleteTag() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        //when
        tagService.delete(1L);
        //then
        verify(tagRepository, times(1)).remove(tag);
    }

    @Test
    void testShouldReturnTagWithSuchName() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.findByName("tag")).thenReturn(Optional.of(tag));
        //when
        Tag actual = tagService.findByName("tag");
        //then
        assertEquals(tag, actual);
    }

    @Test
    void testThrowsNoSuchEntityExceptionWithSuchName() {
        //given
        Tag tag = new Tag(1L, "name");
        when(tagRepository.findByName(tag.getName())).thenReturn(Optional.empty());
        when(translator.toLocale(any())).thenReturn("message");
        //then
        assertThrows(NoSuchEntityException.class, () -> tagService.findByName(tag.getName()));
    }

    @Test
    void testShouldReturnListOfTagsByCertificateId() {
        //given
        List<Tag> tags = Arrays.asList(new Tag(1L, "tag"), new Tag(2L, "tag2"));
        when(tagRepository.findByCertificateId(1L)).thenReturn(tags);
        //when
        List<Tag> actual = tagService.findByCertificateId(1L);
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnListOfTagsWithNames() {
        //given
        List<Tag> tags = Arrays.asList(new Tag(1L, "name"), new Tag(2L, "name2"));
        when(tagRepository.findByNames(List.of("name", "name2"))).thenReturn(tags);
        //when
        List<String> names = Arrays.asList("name", "name2");
        List<Tag> actual = tagService.findByNames(names);
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnCountOfTags() {
        when(tagRepository.count()).thenReturn(10L);
        Long actual = tagService.count();
        assertEquals(10, actual);
    }

    @Test
    void testShouldReturnTagWithName() {
        //given
        Tag tag = new Tag(1L, "tag");
        Tag addTag = new Tag("tag");
        when(tagRepository.findByName(addTag.getName())).thenReturn(Optional.of(tag));
        //when
        Tag actual = tagService.findByNameOrCreate(addTag.getName());
        //then
        assertEquals(tag.getName(), actual.getName());
        assertEquals(1L, actual.getId());
        assertNotNull(actual.getId());
    }

    @Test
    void testShouldCreateTagIfItDoesNotExist() {
        //given
        Tag tag = new Tag(1L, "name");
        Tag addTag = new Tag("name");
        when(tagRepository.findByName(addTag.getName())).thenReturn(Optional.empty());
        when(tagRepository.add(addTag)).thenReturn(tag);
        //when
        Tag actual = tagService.findByNameOrCreate(addTag.getName());
        //then
        assertEquals(tag.getName(), actual.getName());
    }

}