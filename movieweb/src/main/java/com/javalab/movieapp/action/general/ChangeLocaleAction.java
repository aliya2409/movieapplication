package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.utils.LanguageUtils;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateNullOrEmpty;

public class ChangeLocaleAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ChangeLocaleAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(HOME_PAGE, false);
        try {
            String nextAction = validateNullOrEmpty(req.getParameter(LOCATION_PARAM));
            Long languageId = Long.valueOf(validateLong(req.getParameter(LANGUAGE_ID_PARAM)));
            req.getSession().setAttribute(LANGUAGE_ID_ATTRIB, languageId);
            req.getSession().setAttribute(LOCALE_ATTRIB, LanguageUtils.getLanguageIdToLocale().get(languageId.toString()));
            actionResult.setPath(nextAction);
            actionResult.setRedirect(true);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
