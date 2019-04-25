package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.LanguageDto;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
public interface LanguageService {
    void create(LanguageDto language);

    void update(LanguageDto language);

    void delete(Long languageId);

    List<LanguageDto> findAll();

}
