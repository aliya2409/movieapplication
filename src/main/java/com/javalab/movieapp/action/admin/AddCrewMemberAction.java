package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.PersonDAO;
import com.javalab.movieapp.entities.Person;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

public class AddCrewMemberAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddCrewMemberAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String nextDestination = LIST_MOVIE_ACTION;
        try {
            String movieIdParam = validateLong(req.getParameter(MOVIE_ID_PARAM));
            Long movieId = Long.valueOf(movieIdParam);
            String originalName = validateName(req.getParameter(ORIGINAL_NAME_PARAM));
            String originalSurname = validateName(req.getParameter(ORIGINAL_SURNAME_PARAM));
            Integer roleId = Integer.valueOf(validateInteger(req.getParameter(ROLE_ID_PARAM)));
            Person person = new Person();
            person.setOriginalName(originalName);
            person.setOriginalSurname(originalSurname);
            person.setRoleId(roleId);
            PersonDAO personDAO = new PersonDAO();
            personDAO.addPersonToMovieCrew(movieId, person);
            nextDestination = MOVIE_CREW_INFO_ACTION + movieIdParam;
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }

        ActionResult actionResult = new ActionResult(nextDestination, false);
        return actionResult;
    }
}
