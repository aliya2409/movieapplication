package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.entities.Genre;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

public class AddUpdateGenreAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateGenreAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            String genreName = validateName(req.getParameter(NAME_PARAM));
            Genre genre = new Genre();
            genre.setName(genreName);
            GenreDAO genreDAO = new GenreDAO();
            if (ADD_GENRE_ACTION.equals(action)) {
                genreDAO.create(genre, languageId);
            } else if (UPDATE_GENRE_ACTION.equals(action)) {
                Long genreId = Long.valueOf(validateLong(req.getParameter(GENRE_ID_PARAM)));
                genre.setId(genreId);
                genreDAO.update(genre, languageId);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_GENRE_ACTION, false);
        return actionResult;
    }
}
