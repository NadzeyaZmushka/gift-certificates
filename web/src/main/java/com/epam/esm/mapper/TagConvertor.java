package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TagConvertor {

    private final ModelMapper modelMapper;

    public Tag toEntity(TagDTO tagDTO) {
        return Objects.isNull(tagDTO) ? null : modelMapper.map(tagDTO, Tag.class);
    }

    public TagDTO toDTO(Tag tag) {
        return Objects.isNull(tag) ? null : modelMapper.map(tag, TagDTO.class);
    }

}
