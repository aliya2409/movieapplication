package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entity.Genre;
import com.javalab.movieapp.entity.Movie;
import com.javalab.movieapp.entity.User;
import com.javalab.movieapp.validator.IncorrectCrucialParamException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateCrucialParamID;

public class ShowMovieInfoAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ShowMovieInfoAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(MOVIE_PAGE);
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            User user = (User) req.getSession().getAttribute(USER_ATTRIB);
            MovieDAO movieDAO = new MovieDAO();
            GenreDAO genreDAO = new GenreDAO();
            Movie movie = movieDAO.findEntityById(movieId, languageId);
            List<Genre> genres = genreDAO.findMovieGenres(movieId, languageId);
            if (user != null && user.getRoleId() == USER_ROLE_ID) {
                movie = movieDAO.findUsersLikeRate(movie, user.getId());
            }
            movie.setGenres(genres);
            req.setAttribute(MOVIE_ATTRIB, movie);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (IncorrectCrucialParamException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
