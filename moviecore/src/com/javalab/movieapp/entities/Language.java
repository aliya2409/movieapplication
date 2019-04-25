package com.javalab.movieapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.HashMap;

@Entity
@Getter
@Setter
public class Language extends BaseEntity {

    private String name;
}
