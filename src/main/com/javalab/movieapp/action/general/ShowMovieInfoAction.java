package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.Genre;
import com.javalab.movieapp.entities.Movie;
import com.javalab.movieapp.entities.User;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.Formatter.formatDate;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;

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
            Locale locale = new Locale((String) req.getSession().getAttribute(LOCALE_ATTRIB));
            NumberFormat localizedBudget = NumberFormat.getNumberInstance(locale);
            movie.setFormattedBudget((localizedBudget.format(movie.getBudget())));
            movie.setFormattedReleaseDate(formatDate(locale, movie.getReleaseDate()));
            List<Genre> genres = genreDAO.findMovieGenres(movieId, languageId);
            movie.setGenres(genres);
            if (user != null && user.getRoleId() == USER_ROLE_ID) {
                movie = movieDAO.findUsersLikeRate(movie, user.getId());
            }
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
