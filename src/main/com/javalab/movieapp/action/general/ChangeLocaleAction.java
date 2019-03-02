package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.entity.Language;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateLong;

public class ChangeLocaleAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ChangeLocaleAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String requestedFrom = req.getParameter(LOCATION_PARAM);
        String nextAction = HOME_PAGE;
        if (!requestedFrom.isEmpty()) {
            nextAction = requestedFrom.substring(MOVIEAPP_SERVLET_MAPPING.length());
        }
        try {
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            req.getSession().setAttribute(LANGUAGE_ID_ATTRIB, languageId);
            req.getSession().setAttribute(LOCALE_ATTRIB, Language.getLanguageIdToLocale().get(languageId.toString()));
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(nextAction, true);
        return actionResult;
    }
}
