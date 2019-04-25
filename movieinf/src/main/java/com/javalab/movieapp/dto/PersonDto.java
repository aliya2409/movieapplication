package com.javalab.movieapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Getter
@Setter
public class PersonDto {

    private Long id;
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
