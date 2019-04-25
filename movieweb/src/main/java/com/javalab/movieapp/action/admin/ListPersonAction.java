package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.PersonDto;
import com.javalab.movieapp.services.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.Formatter.formatDate;

@Component
public class ListPersonAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListPersonAction.class);

    @Autowired
    private PersonService personService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);

        List<PersonDto> people = personService.findAll(languageId);
        Locale locale = new Locale((String) req.getSession().getAttribute(LOCALE_ATTRIB));
        for(PersonDto person : people){
            person.setFormattedBirthDate(formatDate(locale, person.getBirthDate()));
        }
        req.setAttribute(PERSON_LIST_ATTRIB, people);
        req.setAttribute(ORIGIN_PARAM, LIST_PERSON_ACTION);

        ActionResult actionResult = new ActionResult(PERSON_LIST_PAGE, false);
        return actionResult;
    }
}
