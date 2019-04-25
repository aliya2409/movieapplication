package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.GenreDto;
import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class ShowMovieInfoAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ShowMovieInfoAction.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(MOVIE_PAGE);
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            UserDto user = (UserDto) req.getSession().getAttribute(USER_ATTRIB);
            MovieDto movie = movieService.findEntityById(movieId, languageId);
            Locale locale = new Locale((String) req.getSession().getAttribute(LOCALE_ATTRIB));
            NumberFormat localizedBudget = NumberFormat.getNumberInstance(locale);
            movie.setFormattedBudget((localizedBudget.format(movie.getBudget())));
            movie.setFormattedReleaseDate(formatDate(locale, movie.getReleaseDate()));
            List<GenreDto> genres = genreService.findMovieGenres(movieId, languageId);
            movie.setGenres(genres);
            if (user != null && user.getRoleId() == USER_ROLE_ID) {
                movie = movieService.findUsersLikeRate(movie, user.getId());
            }
            req.setAttribute(MOVIE_ATTRIB, movie);
        } catch (IncorrectCrucialParamException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
