package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

@Component
public class DeleteGenreAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(DeleteGenreAction.class);

    @Autowired
    private GenreService genreService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long genreId = Long.valueOf(validateLong(req.getParameter(GENRE_ID_PARAM)));
            genreService.delete(genreId);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_GENRE_ACTION, false);
        return actionResult;
    }
}
