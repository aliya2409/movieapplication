package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.GenreDAO;
import com.javalab.movieapp.entity.Genre;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

public class ListGenreAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(ListGenreAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(GENRE_LIST_PAGE, false);
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        try {
            GenreDAO genreDAO = new GenreDAO();
            List<Genre> genres = genreDAO.findAll(languageId);
            req.setAttribute(GENRE_LIST_ATTRIB, genres);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        return actionResult;
    }
}
