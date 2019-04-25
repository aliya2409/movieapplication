package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

@Component
public class AddUpdateMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateMovieAction.class);

    @Autowired
    private MovieService movieService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getPathInfo();
        try {
            String originalTitle = validateTitle(req.getParameter(ORIGINAL_TITLE_PARAM));
            LocalTime duration = LocalTime.parse(validateNullOrEmpty(req.getParameter(DURATION_PARAM)));
            Long budget = Long.valueOf(validateLong(req.getParameter(BUDGET_PARAM)));
            LocalDate releaseDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(RELEASE_DATE_PARAM)));
            String description = validateNullOrEmpty(req.getParameter(DESCRIPTION_PARAM));
            Part filePart = req.getPart(IMAGE_PARAM);
            InputStream image = filePart.getInputStream();
            MovieDto movie = new MovieDto();
            movie.setOriginalTitle(originalTitle);
            movie.setDuration(duration);
            movie.setBudget(budget);
            movie.setReleaseDate(releaseDate);
            movie.setDescription(description);
            movie.setImage(IOUtils.toByteArray(image));
            if (ADD_MOVIE_ACTION.equals(action)) {
                movieService.create(movie);
            } else if (UPDATE_MOVIE_ACTION.equals(action)) {
                Long movieId = Long.valueOf(validateLong(req.getParameter(MOVIE_ID_PARAM)));
                movie.setId(movieId);
                movieService.update(movie);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        return actionResult;
    }
}
