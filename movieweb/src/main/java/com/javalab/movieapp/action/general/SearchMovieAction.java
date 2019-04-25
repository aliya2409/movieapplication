package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.GenreDto;
import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateTitle;

@Component
public class SearchMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(SearchMovieAction.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        try {
            String searchingTitle = validateTitle(req.getParameter(SEARCH_PARAM));
            List<MovieDto> movies = movieService.searchMovie(searchingTitle, languageId);
            List<GenreDto> genres;
            for (MovieDto movie : movies) {
                genres = genreService.findMovieGenres(movie.getId(), languageId);
                movie.setGenres(genres);
            }
            req.setAttribute(MOVIE_LIST_ATTRIB, movies);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(MOVIE_LIST_PAGE, false);
        return actionResult;
    }
}
