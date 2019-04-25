package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

@Component
public class DeleteMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(DeleteMovieAction.class);

    @Autowired
    private MovieService movieService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long movieId = Long.valueOf(validateLong(req.getParameter(MOVIE_ID_PARAM)));
            movieService.delete(movieId);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        return actionResult;
    }
}
