package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.Movie;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

public class AddUpdateMovieLocalAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateMovieLocalAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            String originalTitle = validateTitle(req.getParameter(ORIGINAL_TITLE_PARAM));
            String translatedTitle = validateTitle(req.getParameter(TRANSLATED_TITLE_PARAM));
            String description = validateNullOrEmpty(req.getParameter(DESCRIPTION_PARAM));
            Movie movie = new Movie();
            movie.setOriginalTitle(originalTitle);
            movie.setTranslatedTitle(translatedTitle);
            movie.setDescription(description);
            MovieDAO movieDAO = new MovieDAO();
            if (CREATE_MOVIE_LOCAL_INFO_ACTION.equals(action) && translatedTitle != null && description != null) {
                movieDAO.addMovieInfo(movie, languageId);
            } else if (UPDATE_MOVIE_LOCAL_INFO_ACTION.equals(action) && translatedTitle != null && description != null) {
                movieDAO.updateMovieInfo(movie, languageId);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_LANGUAGE_ACTION, false);
        return actionResult;
    }
}
