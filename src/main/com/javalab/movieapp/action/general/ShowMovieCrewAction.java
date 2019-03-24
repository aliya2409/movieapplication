package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.PersonDAO;
import com.javalab.movieapp.entities.Person;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;

public class ShowMovieCrewAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ShowMovieCrewAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(PERSON_LIST_PAGE);
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            PersonDAO personDAO = new PersonDAO();
            List<Person> movieCrew = personDAO.findMovieCrew(movieId, languageId);
            req.setAttribute(MOVIE_ID_PARAM, movieId);
            req.setAttribute(PERSON_LIST_ATTRIB, movieCrew);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (IncorrectCrucialParamException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
