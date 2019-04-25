package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

@Component
public class AddUpdateMovieLocalAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateMovieLocalAction.class);

    @Autowired
    private MovieService movieService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            String originalTitle = validateTitle(req.getParameter(ORIGINAL_TITLE_PARAM));
            String translatedTitle = validateTitle(req.getParameter(TRANSLATED_TITLE_PARAM));
            String description = validateNullOrEmpty(req.getParameter(DESCRIPTION_PARAM));
            MovieDto movie = new MovieDto();
            movie.setOriginalTitle(originalTitle);
            movie.setTranslatedTitle(translatedTitle);
            movie.setDescription(description);
            if (CREATE_MOVIE_LOCAL_INFO_ACTION.equals(action) && translatedTitle != null && description != null) {
                movieService.addMovieInfo(movie, languageId);
            } else if (UPDATE_MOVIE_LOCAL_INFO_ACTION.equals(action) && translatedTitle != null && description != null) {
                movieService.updateMovieInfo(movie, languageId);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_LANGUAGE_ACTION, false);
        return actionResult;
    }
}
