package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.PersonDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public void addPersonToMovieCrew(Long movieId, PersonDto person) {

    }

    @Override
    public void create(PersonDto person) {

    }

    @Override
    public void update(PersonDto person) {

    }

    @Override
    public void addPersonInfo(PersonDto person, Long languageId) {

    }

    @Override
    public void updatePersonInfo(PersonDto person, Long languageId) {

    }

    @Override
    public void deletePersonFromMovieCrew(Long movieId, Long memberId) {

    }

    @Override
    public void delete(Long personId) {

    }

    @Override
    public List<PersonDto> findAll(Long languageId) {
        return null;
    }

    @Override
    public List<PersonDto> findMovieCrew(Long movieId, Long languageId) {
        return null;
    }
}
