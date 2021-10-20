package com.epam.esm.repository;

import com.epam.esm.config.TestRepositoryConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.specification.impl.tag.TagFindAllSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNamesSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class TagRepositoryTest {

    private static final Tag tag2 = new Tag(2L, "tag2");
    private static final Tag tag3 = new Tag(3L, "tag3");
    private static final Tag tag4 = new Tag(4L, "tag4");
    private static final Tag tag5 = new Tag(5L, "tag5");
    private static final Tag tag6 = new Tag(6L, "tag6");
    private static final Tag tag7 = new Tag(7L, "tag7");
    private static final Tag tag8 = new Tag(8L, "new tag");

    @Autowired
    private BaseCrudRepository<Tag> tagRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    TagMapper tagMapper;


    @Test
    void testShouldCreateTag() {
        Tag expected = new Tag("new tag");
        tagRepository.add(expected);
        expected.setId(8L);

        Tag actual = jdbcTemplate.queryForObject("SELECT * FROM gifts.tag WHERE name = ?", tagMapper, expected.getName());

        assertEquals(expected, actual);
    }

    @Test
    void testShouldReturnAllTags() {
        List<Tag> expected = Arrays.asList(tag2, tag3, tag4, tag5, tag6, tag7, tag8);
        List<Tag> actual = tagRepository.queryForList(new TagFindAllSpecification());

        assertEquals(expected, actual);
    }

    @Test
    void testShouldReturnTagWithSuchId() {
        Optional<Tag> tagOptional = Optional.of(tag2);
        Optional<Tag> actual = tagRepository.queryForOne(new TagFindByIdSpecification(2L));

        assertEquals(tagOptional, actual);
    }

    @Test
    void testShouldReturnTagWithSuchName() {
        Tag actual = tagRepository.queryForOne(new TagFindByNameSpecification("tag6")).orElse(null);

        assertEquals(tag6, actual);
    }

    @Test
    void testShouldReturnListOfTagsWithSuchNames() {
        List<Tag> tags = Arrays.asList(tag4, tag5, tag6);
        List<Tag> actual = tagRepository.queryForList(new TagFindByNamesSpecification(
                Arrays.asList("tag4", "tag5", "tag6")));

        assertEquals(tags.size(), actual.size());
        assertEquals(tags, actual);
    }

    @Test
    void testShouldReturnTagsByCertificateId() {
        Long certificateId = 2L;
        List<Tag> tags = Arrays.asList(tag4, tag5, tag7);
        List<Tag> actual = tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificateId));

        assertEquals(tags, actual);
    }

    @Test
    void testShouldDeleteTag() {
        Long id = 1L;
        jdbcTemplate.update("DELETE FROM gifts.certificate_tag WHERE tag_id = ?",
                id);
        boolean isDeleted = tagRepository.remove(new Tag(1L, "tag1"));
        assertTrue(isDeleted);

        List<Tag> actual = jdbcTemplate.query("SELECT * FROM gifts.tag WHERE id = ?", tagMapper, id);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testShouldReturnEmptyOptionalWhenThereIsNoTagWithSuchId() {
        Optional<Tag> tagOptional = tagRepository.queryForOne(new TagFindByIdSpecification(0L));

        assertTrue(tagOptional.isEmpty());
    }

    @Test
    void testShouldReturnEmptyOptionalWhenThereIsNoTagWithSuchName() {
        Optional<Tag> tagOptional = tagRepository.queryForOne(new TagFindByNameSpecification("name"));

        assertTrue(tagOptional.isEmpty());
    }

}