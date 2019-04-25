package com.javalab.movieapp.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Genre extends BaseEntity {

    private String name;

    private Long languageId;

    @Override
    public String toString() {
        return name;
    }
}
