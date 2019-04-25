package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.GenreDto;
import com.javalab.movieapp.dto.LanguageDto;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.services.LanguageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

@Component
public class ListGenreAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListGenreAction.class);

    @Autowired
    private GenreService genreService;

    @Autowired
    private LanguageService languageService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(GENRE_LIST_PAGE, false);
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        List<GenreDto> genres = genreService.findAll(languageId);
        req.setAttribute(GENRE_LIST_ATTRIB, genres);
        List<LanguageDto> availableLanguages = languageService.findAll();
        req.setAttribute(LANGUAGE_LIST_ATTRIB, availableLanguages);
        return actionResult;
    }
}
