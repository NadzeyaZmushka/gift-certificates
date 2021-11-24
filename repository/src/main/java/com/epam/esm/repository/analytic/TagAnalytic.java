package com.epam.esm.repository.analytic;

import com.epam.esm.entity.Tag;
import lombok.Data;

@Data
public class TagAnalytic {

    private Long userId;
    private Tag tag;
    private Integer count;

}
