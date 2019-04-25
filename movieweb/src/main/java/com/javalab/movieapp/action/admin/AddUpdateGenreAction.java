package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.GenreDto;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

@Component
public class AddUpdateGenreAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateGenreAction.class);

    @Autowired
    private GenreService genreService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            String genreName = validateName(req.getParameter(NAME_PARAM));
            GenreDto genre = new GenreDto();
            genre.setName(genreName);
            if (ADD_GENRE_ACTION.equals(action)) {
                genreService.create(genre, languageId);
            } else if (UPDATE_GENRE_ACTION.equals(action)) {
                Long genreId = Long.valueOf(validateLong(req.getParameter(GENRE_ID_PARAM)));
                genre.setId(genreId);
                genreService.update(genre, languageId);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_GENRE_ACTION, false);
        return actionResult;
    }
}
