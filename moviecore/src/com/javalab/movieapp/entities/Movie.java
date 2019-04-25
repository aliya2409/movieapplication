package com.javalab.movieapp.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseEntity {

    private String originalTitle;
    private LocalTime duration;
    private long budget;
    private String formattedBudget;
    private LocalDate releaseDate;
    private String formattedReleaseDate;
    private byte[] image;
    private String translatedTitle;
    private String description;
    @ManyToMany
    private List<Person> crew;
    @ManyToMany
    private List<Genre> genres;
    private float rating;
    private boolean isLiked;
    private int rate;
    private String imageBase64;

}
