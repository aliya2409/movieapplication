package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.services.PersonService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

@Component
public class DeletePersonAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(DeletePersonAction.class);

    @Autowired
    private PersonService personService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            Long personId = Long.valueOf(validateLong(req.getParameter(PERSON_ID_PARAM)));
            personService.delete(personId);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_PERSON_ACTION, false);
        return actionResult;
    }
}
