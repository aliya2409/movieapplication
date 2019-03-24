package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.PersonDAO;
import com.javalab.movieapp.entities.Person;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

public class AddUpdatePersonAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdatePersonAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getPathInfo();
        try {
            String originalName = validateName(req.getParameter(ORIGINAL_NAME_PARAM));
            String originalSurname = validateName(req.getParameter(ORIGINAL_SURNAME_PARAM));
            LocalDate birthDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(BIRTHDATE_PARAM)));
            Part filePart = req.getPart(IMAGE_PARAM);
            InputStream image = filePart.getInputStream();
            Person person = new Person();
            person.setOriginalName(originalName);
            person.setOriginalSurname(originalSurname);
            person.setBirthDate(birthDate);
            person.setImage(image);
            PersonDAO personDAO = new PersonDAO();
            if (ADD_PERSON_ACTION.equals(action)) {
                personDAO.create(person);
            } else if (UPDATE_PERSON_ACTION.equals(action)) {
                Long personId = Long.valueOf(validateLong(req.getParameter(PERSON_ID_PARAM)));
                person.setId(personId);
                personDAO.update(person);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_PERSON_ACTION, false);
        return actionResult;
    }
}
