package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.MovieDto;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 22-Apr-19.
 */
public interface MovieService {

    void rateMovie(Long movieId, Long id, Integer rate);

    void updateMovieRating(Long movieId);

    List<MovieDto> findLikedMovies(Long userId, Long languageId);

    void create(MovieDto movie);

    void update(MovieDto movie);

    void addMovieInfo(MovieDto movie, Long languageId);

    void updateMovieInfo(MovieDto movie, Long languageId);

    void delete(Long movieId);

    List<MovieDto> findAll(Long languageId);

    List<MovieDto> searchMovie(String searchingTitle, Long languageId);

    MovieDto findEntityById(Long movieId, Long languageId);

    MovieDto findUsersLikeRate(MovieDto movie, Long id);

    void likeMovie(Long movieId, Long userId);

    void unlikeMovie(Long movieId, Long userId);
}
