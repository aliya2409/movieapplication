package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.PersonDto;
import com.javalab.movieapp.services.PersonService;
import com.javalab.movieapp.utils.validators.IncorrectCrucialParamException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.Formatter.formatDate;
import static com.javalab.movieapp.utils.validators.InputValidator.validateCrucialParamID;

@Component
public class ShowMovieCrewAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ShowMovieCrewAction.class);

    @Autowired
    private PersonService personService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        try {
            Long movieId = Long.valueOf(validateCrucialParamID(req.getParameter(MOVIE_ID_PARAM)));
            actionResult.setPath(PERSON_LIST_PAGE);
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            List<PersonDto> movieCrew = personService.findMovieCrew(movieId, languageId);
            Locale locale = new Locale((String) req.getSession().getAttribute(LOCALE_ATTRIB));
            for (PersonDto person : movieCrew) {
                person.setFormattedBirthDate(formatDate(locale, person.getBirthDate()));
            }
            req.setAttribute(MOVIE_ID_PARAM, movieId);
            req.setAttribute(PERSON_LIST_ATTRIB, movieCrew);
        } catch (IncorrectCrucialParamException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_CRUCIAL_URL_PARAM_ERROR);
        }
        return actionResult;
    }
}
