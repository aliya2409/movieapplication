package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entity.Genre;
import com.javalab.movieapp.entity.Movie;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateTitle;

public class SearchMovieAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(SearchMovieAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        try {
            String searchingTitle = validateTitle(req.getParameter(SEARCH_PARAM));
            MovieDAO movieDAO = new MovieDAO();
            List<Movie> movies = movieDAO.searchMovie(searchingTitle, languageId);
            List<Genre> genres;
            GenreDAO genreDAO = new GenreDAO();
            for (Movie movie : movies) {
                genres = genreDAO.findMovieGenres(movie.getId(), languageId);
                movie.setGenres(genres);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(MOVIE_LIST_PAGE, false);
        return actionResult;
    }
}
