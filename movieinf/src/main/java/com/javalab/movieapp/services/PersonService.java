package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.PersonDto;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
public interface PersonService {

    void addPersonToMovieCrew(Long movieId, PersonDto person);

    void create(PersonDto person);

    void update(PersonDto person);

    void addPersonInfo(PersonDto person, Long languageId);

    void updatePersonInfo(PersonDto person, Long languageId);

    void deletePersonFromMovieCrew(Long movieId, Long memberId);

    void delete(Long personId);

    List<PersonDto> findAll(Long languageId);

    List<PersonDto> findMovieCrew(Long movieId, Long languageId);
}
