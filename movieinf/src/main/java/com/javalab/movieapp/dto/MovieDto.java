package com.javalab.movieapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Getter
@Setter
public class MovieDto {

    private Long id;
    private String originalTitle;
    private LocalTime duration;
    private long budget;
    private String formattedBudget;
    private LocalDate releaseDate;
    private String formattedReleaseDate;
    private byte[] image;
    private String translatedTitle;
    private String description;
    private List<PersonDto> crew;
    private List<GenreDto> genres;
    private float rating;
    private boolean isLiked;
    private int rate;
    private String imageBase64;
}
