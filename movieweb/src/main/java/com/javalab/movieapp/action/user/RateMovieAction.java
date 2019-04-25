package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;
import static com.javalab.movieapp.utils.validators.InputValidator.validateMovieRate;

public class RateMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(RateMovieAction.class);

    @Autowired
    private MovieService movieService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(MOVIE_INFO_ACTION + movieId);
            Integer rate = Integer.valueOf(validateMovieRate(req.getParameter(RATE_PARAM)));
            UserDto user = (UserDto) req.getSession().getAttribute(USER_ATTRIB);
            movieService.rateMovie(movieId, user.getId(), rate);
            movieService.updateMovieRating(movieId);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        } catch (IncorrectCrucialParamException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
