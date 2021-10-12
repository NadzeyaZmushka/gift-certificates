package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import com.epam.esm.repository.specification.impl.TagFindByNameSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.TAG_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final TagValidator tagValidator;
    private final TagDTOMapper mapper;

    @Override
    public TagDTO add(TagDTO tag) {
        tagValidator.validName(tag.getName());
        return mapper.toDTO(tagRepository.add(mapper.toEntity(tag)));
    }

    @Override
    public List<TagDTO> findAll() {
        return tagRepository.queryForList(new FindAllSpecification("tag"))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(long id) {
        return mapper.toDTO(tagRepository.queryForOne(new FindByIdSpecification("tag", id))
                .orElseThrow(() -> new NoSuchEntityException("No tag with id = " + id
                        , TAG_NOT_FOUND.getErrorCode())));
    }

    @Override
    public void delete(Long id) {
        TagDTO tagDTO = findById(id);
        if (tagDTO == null) {
            log.error("There is no tag with id = " + id);
            throw new NoSuchEntityException("There is no tag with id = " + id + " in database"
                    , TAG_NOT_FOUND.getErrorCode());
        }
        Tag tag = mapper.toEntity(tagDTO);
        tagRepository.remove(tag);
    }

    @Override
    public TagDTO findByName(String name) {
        return mapper.toDTO(tagRepository.queryForOne(new TagFindByNameSpecification(name))
        .orElseThrow(() -> new NoSuchEntityException("No tag with name: " + name
                , TAG_NOT_FOUND.getErrorCode())));
    }

}
