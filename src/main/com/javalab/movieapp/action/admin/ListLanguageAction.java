package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.LanguageDAO;
import com.javalab.movieapp.entities.Language;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

public class ListLanguageAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListLanguageAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(LANGUAGE_LIST_PAGE, false);
        try {
            LanguageDAO languageDAO = new LanguageDAO();
            List<Language> languages = languageDAO.findAll();
            req.setAttribute(LANGUAGE_LIST_ATTRIB, languages);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        return actionResult;
    }
}
