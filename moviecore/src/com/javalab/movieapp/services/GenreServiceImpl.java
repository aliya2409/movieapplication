package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.GenreDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
@Service
public class GenreServiceImpl implements GenreService {
    @Override
    public void addGenreLocale(Long genreId, Long languageId, String genreName) {

    }

    @Override
    public void addGenreToMovie(Long movieId, String genreName) {

    }

    @Override
    public void create(GenreDto genre, Long languageId) {

    }

    @Override
    public void update(GenreDto genre, Long languageId) {

    }

    @Override
    public void delete(Long genreId) {

    }

    @Override
    public List<GenreDto> findAll(Long languageId) {
        return null;
    }

    @Override
    public List<GenreDto> findMovieGenres(Long id, Long languageId) {
        return null;
    }

    @Override
    public void deleteMovieGenre(Long movieId, Long genreId) {

    }
}
