package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.Genre;
import com.javalab.movieapp.entities.Movie;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.Formatter.formatDate;

public class ListMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListMovieAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ActionResult actionResult = new ActionResult(MOVIE_LIST_PAGE, false);
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        Locale locale = new Locale((String) req.getSession().getAttribute(LOCALE_ATTRIB));
        try {
            List<Movie> movies = movieDAO.findAll(languageId);
            for (Movie movie : movies) {
                movie.setFormattedReleaseDate(formatDate(locale, movie.getReleaseDate()));
                List<Genre> genres = genreDAO.findMovieGenres(movie.getId(), languageId);
                movie.setGenres(genres);
                req.setAttribute(ORIGIN_PARAM, LIST_MOVIE_ACTION);
                req.setAttribute(MOVIE_LIST_ATTRIB, movies);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        return actionResult;
    }

}
