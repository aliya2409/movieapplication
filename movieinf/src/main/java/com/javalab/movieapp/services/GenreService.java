package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.GenreDto;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
public interface GenreService {

    void addGenreLocale(Long genreId, Long languageId, String genreName);

    void addGenreToMovie(Long movieId, String genreName);

    void create(GenreDto genre, Long languageId);

    void update(GenreDto genre, Long languageId);

    void delete(Long genreId);

    List<GenreDto> findAll(Long languageId);

    List<GenreDto> findMovieGenres(Long id, Long languageId);

    void deleteMovieGenre(Long movieId, Long genreId);
}
