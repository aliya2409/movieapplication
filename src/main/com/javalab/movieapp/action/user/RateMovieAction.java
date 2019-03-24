package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.User;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;
import static com.javalab.movieapp.utils.validators.InputValidator.validateMovieRate;

public class RateMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(RateMovieAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(MOVIE_INFO_ACTION + movieId);
            Integer rate = Integer.valueOf(validateMovieRate(req.getParameter(RATE_PARAM)));
            User user = (User) req.getSession().getAttribute(USER_ATTRIB);
            MovieDAO movieDAO = new MovieDAO();
            movieDAO.rateMovie(movieId, user.getId(), rate);
            movieDAO.updateMovieRating(movieId);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
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
