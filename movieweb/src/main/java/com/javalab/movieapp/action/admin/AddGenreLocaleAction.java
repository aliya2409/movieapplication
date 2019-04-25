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
import java.sql.SQLException;
import java.util.Objects;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

@Component
public class AddGenreLocaleAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddGenreLocaleAction.class);

    @Autowired
    private GenreService genreService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long UILanguageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            if (!Objects.equals(UILanguageId, languageId)) {
                Long genreId = Long.valueOf(validateLong(req.getParameter(GENRE_ID_PARAM)));
                String genreName = validateName(req.getParameter(NAME_PARAM));
                genreService.addGenreLocale(genreId, languageId, genreName);
            } else {
                req.setAttribute(ERROR_ATTRIB, LOCALE_DATA_EXISTS_ERROR);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_GENRE_ACTION, false);
        return actionResult;
    }
}
