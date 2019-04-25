package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.LanguageDto;
import com.javalab.movieapp.services.LanguageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

@Component
public class ListLanguageAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListLanguageAction.class);

    @Autowired
    private LanguageService languageService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(LANGUAGE_LIST_PAGE, false);
        List<LanguageDto> languages = languageService.findAll();
        req.setAttribute(LANGUAGE_LIST_ATTRIB, languages);

        return actionResult;
    }
}
