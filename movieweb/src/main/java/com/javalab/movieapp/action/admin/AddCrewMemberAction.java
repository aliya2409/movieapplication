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
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

@Component
public class AddCrewMemberAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddCrewMemberAction.class);

    @Autowired
    private PersonService personService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String nextDestination = LIST_MOVIE_ACTION;
        try {
            String movieIdParam = validateLong(req.getParameter(MOVIE_ID_PARAM));
            Long movieId = Long.valueOf(movieIdParam);
            String originalName = validateName(req.getParameter(ORIGINAL_NAME_PARAM));
            String originalSurname = validateName(req.getParameter(ORIGINAL_SURNAME_PARAM));
            Integer roleId = Integer.valueOf(validateInteger(req.getParameter(ROLE_ID_PARAM)));
            PersonDto person = new PersonDto();
            person.setOriginalName(originalName);
            person.setOriginalSurname(originalSurname);
            person.setRoleId(roleId);
            personService.addPersonToMovieCrew(movieId, person);
            nextDestination = MOVIE_CREW_INFO_ACTION + movieIdParam;
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }

        ActionResult actionResult = new ActionResult(nextDestination, false);
        return actionResult;
    }
}
