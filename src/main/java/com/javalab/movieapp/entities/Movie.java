package com.javalab.movieapp.entities;


import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Movie extends BaseEntity {

    private String originalTitle;
    private LocalTime duration;
    private long budget;
    private String formattedBudget;
    private LocalDate releaseDate;
    private String formattedReleaseDate;
    private InputStream image;
    private String translatedTitle;
    private String description;
    private List<Person> crew;
    private List<Genre> genres;
    private float rating;
    private boolean isLiked;
    private int rate;
    private String imageBase64;


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getTranslatedTitle() {
        return translatedTitle;
    }

    public void setTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getCrew() {
        return crew;
    }

    public void setCrew(List<Person> crew) {
        this.crew = crew;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getFormattedBudget() {
        return formattedBudget;
    }

    public void setFormattedBudget(String formattedBudget) {
        this.formattedBudget = formattedBudget;
    }

    public String getFormattedReleaseDate() {
        return formattedReleaseDate;
    }

    public void setFormattedReleaseDate(String formattedReleaseDate) {
        this.formattedReleaseDate = formattedReleaseDate;
    }
}
