package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WidelyUsedTagStatistic {

    private Long userId;
    private Set<Tag> tags;

}
