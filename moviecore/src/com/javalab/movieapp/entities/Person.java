package com.javalab.movieapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.InputStream;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Person extends BaseEntity {

    private String originalName;
    private String originalSurname;
    private String translatedName;
    private String translatedSurname;
    private LocalDate birthDate;
    private String formattedBirthDate;
    private byte[] image;
    private int roleId;
    private String imageBase64;

}
