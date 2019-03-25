package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.LanguageDAO;
import com.javalab.movieapp.entities.Language;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

public class AddUpdateLanguageAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateLanguageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            String languageName = validateName(req.getParameter(NAME_PARAM));
            Language language = new Language();
            language.setName(languageName);
            LanguageDAO languageDAO = new LanguageDAO();
            if (ADD_LANGUAGE_ACTION.equals(action)) {
                languageDAO.create(language);
            } else if (UPDATE_LANGUAGE_ACTION.equals(action)) {
                Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
                language.setId(languageId);
                languageDAO.update(language);
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
