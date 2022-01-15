package com.epam.esm.converter;

import com.epam.esm.analytic.WidelyUsedTagStatistic;
import com.epam.esm.dto.WidelyUsedTagsDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WidelyUsedTagConverter {

    private final ModelMapper modelMapper;

    public WidelyUsedTagStatistic toEntity(WidelyUsedTagsDTO widelyUsedTagsDto) {
        return Objects.isNull(widelyUsedTagsDto) ? null : modelMapper.map(widelyUsedTagsDto, WidelyUsedTagStatistic.class);
    }

    public WidelyUsedTagsDTO toDTO(WidelyUsedTagStatistic usedTagStatistic) {
        return Objects.isNull(usedTagStatistic) ? null : modelMapper.map(usedTagStatistic, WidelyUsedTagsDTO.class);
    }

}
