package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorConstants;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
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

    private static final String TABLE = "tag";

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
        return tagRepository.queryForList(new FindAllSpecification(TABLE))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(long id) {
        return mapper.toDTO(tagRepository.queryForOne(new FindByIdSpecification(TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_ID + id
                        , TAG_NOT_FOUND.getErrorCode())));
    }

    @Override
    public void delete(Long id) {
        TagDTO tagDTO = findById(id);
        if (tagDTO == null) {
            log.error(ErrorConstants.NO_TAG_WITH_ID + id);
            throw new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_ID + id, TAG_NOT_FOUND.getErrorCode());
        }
        Tag tag = mapper.toEntity(tagDTO);
        tagRepository.remove(tag);
    }

    @Override
    public TagDTO findByName(String name) {
        return mapper.toDTO(tagRepository.queryForOne(new TagFindByNameSpecification(name))
        .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_NAME + name
                , TAG_NOT_FOUND.getErrorCode())));
    }

}
