package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Objects;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

public class AddGenreLocaleAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddGenreLocaleAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long UILanguageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            if (!Objects.equals(UILanguageId, languageId)) {
                Long genreId = Long.valueOf(validateLong(req.getParameter(GENRE_ID_PARAM)));
                String genreName = validateName(req.getParameter(NAME_PARAM));
                GenreDAO genreDAO = new GenreDAO();
                genreDAO.addGenreLocale(genreId, languageId, genreName);
            } else {
                req.setAttribute(ERROR_ATTRIB, LOCALE_DATA_EXISTS_ERROR);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_GENRE_ACTION, false);
        return actionResult;
    }
}
