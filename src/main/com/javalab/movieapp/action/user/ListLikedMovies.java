package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.Genre;
import com.javalab.movieapp.entities.Movie;
import com.javalab.movieapp.entities.User;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

public class ListLikedMovies implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListLikedMovies.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            User currentUser = (User) req.getSession().getAttribute(USER_ATTRIB);
            Long userId = Long.valueOf(validateLong(currentUser.getId().toString()));
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            MovieDAO movieDAO = new MovieDAO();
            GenreDAO genreDAO = new GenreDAO();
            List<Movie> likedMovies = movieDAO.findLikedMovies(userId, languageId);
            for (Movie movie : likedMovies) {
                List<Genre> genres = genreDAO.findMovieGenres(movie.getId(), languageId);
                movie.setGenres(genres);
            }
            req.setAttribute(MOVIE_LIST_ATTRIB, likedMovies);
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
