package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.PersonDto;
import com.javalab.movieapp.services.PersonService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateName;

@Component
public class AddUpdatePersonLocalAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdatePersonLocalAction.class);

    @Autowired
    private PersonService personService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            String originalName = validateName(req.getParameter(ORIGINAL_NAME_PARAM));
            String originalSurname = validateName(req.getParameter(ORIGINAL_SURNAME_PARAM));
            String translatedName = validateName(req.getParameter(TRANSLATED_NAME_PARAM));
            String translatedSurname = validateName(req.getParameter(TRANSLATED_SURNAME_PARAM));
            PersonDto person = new PersonDto();
            person.setOriginalName(originalName);
            person.setOriginalSurname(originalSurname);
            person.setTranslatedName(translatedName);
            person.setTranslatedSurname(translatedSurname);
            if (CREATE_PERSON_LOCAL_INFO_ACTION.equals(action)) {
                personService.addPersonInfo(person, languageId);
            } else if (UPDATE_PERSON_LOCAL_INFO_ACTION.equals(action)) {
                personService.updatePersonInfo(person, languageId);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_LANGUAGE_ACTION, false);
        return actionResult;
    }
}
