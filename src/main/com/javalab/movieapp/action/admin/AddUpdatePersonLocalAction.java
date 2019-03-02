package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.PersonDAO;
import com.javalab.movieapp.entity.Person;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateLong;
import static com.javalab.movieapp.validator.InputValidator.validateName;

public class AddUpdatePersonLocalAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(AddUpdatePersonLocalAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            String originalName = validateName(req.getParameter(ORIGINAL_NAME_PARAM));
            String originalSurname = validateName(req.getParameter(ORIGINAL_SURNAME_PARAM));
            String translatedName = validateName(req.getParameter(TRANSLATED_NAME_PARAM));
            String translatedSurname = validateName(req.getParameter(TRANSLATED_SURNAME_PARAM));
            Person person = new Person();
            person.setOriginalName(originalName);
            person.setOriginalSurname(originalSurname);
            person.setTranslatedName(translatedName);
            person.setTranslatedSurname(translatedSurname);
            PersonDAO personDAO = new PersonDAO();
            if (CREATE_PERSON_LOCAL_INFO_ACTION.equals(action)) {
                personDAO.addPersonInfo(person, languageId);
            } else if (UPDATE_PERSON_LOCAL_INFO_ACTION.equals(action)) {
                personDAO.updatePersonInfo(person, languageId);
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
