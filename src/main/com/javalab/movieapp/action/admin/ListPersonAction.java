package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.PersonDAO;
import com.javalab.movieapp.entity.Person;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

public class ListPersonAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ListPersonAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        PersonDAO personDAO = new PersonDAO();
        try {
            List<Person> people = personDAO.findAll(languageId);
            req.setAttribute(PERSON_LIST_ATTRIB, people);
            req.setAttribute(ORIGIN_PARAM, LIST_PERSON_ACTION);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        ActionResult actionResult = new ActionResult(PERSON_LIST_PAGE, false);
        return actionResult;
    }
}
