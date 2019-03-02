package com.javalab.movieapp.entity;

import java.io.InputStream;
import java.time.LocalDate;

public class Person extends BaseEntity {
    private String originalName;
    private String originalSurname;
    private String translatedName;
    private String translatedSurname;
    private LocalDate birthDate;
    private InputStream image;
    private int roleId;
    private String imageBase64;


    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalSurname() {
        return originalSurname;
    }

    public void setOriginalSurname(String originalSurname) {
        this.originalSurname = originalSurname;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getTranslatedSurname() {
        return translatedSurname;
    }

    public void setTranslatedSurname(String translatedSurname) {
        this.translatedSurname = translatedSurname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
