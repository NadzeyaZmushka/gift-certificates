package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class WidelyUsedTagsByUser {

    private Long userId;
    private Set<Tag> tags;

}
