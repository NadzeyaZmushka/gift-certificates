package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNamesSpecification;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private BaseCrudRepository<Tag> tagRepository;
    @Mock
    private TagValidator tagValidator;
    @Mock
    private Translator translator;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void testShouldAddTag() {
        //given
        Tag tag = new Tag("tag");
        when(tagRepository.add(tag)).thenReturn(new Tag(1L, "tag"));
        //when
        Tag actual = tagService.add(tag);
        //then
        assertEquals(tag.getName(), actual.getName());
        assertNotNull(actual.getId());
    }

    @Test
    void testShouldReturnAllTags() {
        //given
        List<Tag> tags = Arrays.asList(new Tag(1L, "tag"), new Tag());
        when(tagRepository.queryForList(any(FindAllSpecification.class))).thenReturn(tags);
        //when
        List<Tag> actual = tagService.findAll();
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnTagWithSuchId() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.queryForOne(any(FindByIdSpecification.class))).thenReturn(Optional.of(tag));
        //when
        Tag expectedTag = tagService.findById(1L);
        //then
        assertEquals(expectedTag, tag);
    }

    @Test
    void delete() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.queryForOne(any(FindByIdSpecification.class))).thenReturn(Optional.of(tag));
        //when
        tagService.delete(1L);
        //then
        verify(tagRepository, times(1)).remove(tag);
    }

    @Test
    void testShouldReturnTagWithSuchName() {
        //given
        Tag tag = new Tag(1L, "tag");
        when(tagRepository.queryForOne(any(TagFindByNameSpecification.class))).thenReturn(Optional.of(tag));
        //when
        Tag actual = tagService.findByName("tag");
        //then
        assertEquals(tag, actual);

    }

    @Test
    void testShouldReturnListOfTagsByCertificateId() {
        //given
        List<Tag> tags = Arrays.asList(new Tag(1L, "tag"), new Tag());
        when(tagRepository.queryForList(any(TagFindByCertificateIdSpecification.class))).thenReturn(tags);
        //when
        List<Tag> actual = tagService.findByCertificateId(1L);
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnListOfTagsWithNames() {
        //given
        List<Tag> tags = Arrays.asList(new Tag("name"), new Tag("name2"));
        when(tagRepository.queryForList(any(TagFindByNamesSpecification.class))).thenReturn(tags);
        //when
        List<String> names = Arrays.asList("name", "name2");
        List<Tag> actual = tagService.findByNames(names);
        //then
        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

}