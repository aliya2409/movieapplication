package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateLong;

public class DeleteMovieAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(DeleteMovieAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long movieId = Long.valueOf(validateLong(req.getParameter(MOVIE_ID_PARAM)));
            MovieDAO movieDAO = new MovieDAO();
            movieDAO.delete(movieId);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        return actionResult;
    }
}
