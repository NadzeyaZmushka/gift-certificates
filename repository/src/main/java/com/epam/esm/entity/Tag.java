package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tag", schema = "gifts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

}
