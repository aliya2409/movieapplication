package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.LanguageDto;
import com.javalab.movieapp.services.LanguageService;
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
public class AddUpdateLanguageAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateLanguageAction.class);

    @Autowired
    private LanguageService languageService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {
            String languageName = validateName(req.getParameter(NAME_PARAM));
            LanguageDto language = new LanguageDto();
            language.setName(languageName);
            if (ADD_LANGUAGE_ACTION.equals(action)) {
                languageService.create(language);
            } else if (UPDATE_LANGUAGE_ACTION.equals(action)) {
                Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
                language.setId(languageId);
                languageService.update(language);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_LANGUAGE_ACTION, false);
        return actionResult;
    }
}
