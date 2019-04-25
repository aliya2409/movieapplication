package com.javalab.movieapp.services;

import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.entities.Movie;
import com.javalab.movieapp.services.MovieService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dauren_Altynbekov on 22-Apr-19.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Override
    public void rateMovie(Long movieId, Long id, Integer rate) {

    }

    @Override
    public void updateMovieRating(Long movieId) {

    }

    @Override
    public List<MovieDto> findLikedMovies(Long userId, Long languageId) {
        return null;
    }

    @Override
    public void create(MovieDto movie) {

    }

    @Override
    public void update(MovieDto movie) {

    }

    @Override
    public void addMovieInfo(MovieDto movie, Long languageId) {

    }

    @Override
    public void updateMovieInfo(MovieDto movie, Long languageId) {

    }

    @Override
    public void delete(Long movieId) {

    }

    @Override
    public List<MovieDto> findAll(Long languageId) {
        return null;
    }

    @Override
    public List<MovieDto> searchMovie(String searchingTitle, Long languageId) {
        return null;
    }

    @Override
    public MovieDto findEntityById(Long movieId, Long languageId) {
        return null;
    }

    @Override
    public MovieDto findUsersLikeRate(MovieDto movie, Long id) {
        return null;
    }

    @Override
    public void likeMovie(Long movieId, Long userId) {

    }

    @Override
    public void unlikeMovie(Long movieId, Long userId) {

    }
}
