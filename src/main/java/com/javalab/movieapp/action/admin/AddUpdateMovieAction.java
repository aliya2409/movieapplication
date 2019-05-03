package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.MovieDAO;
import com.javalab.movieapp.entities.Movie;
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
import java.time.LocalTime;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

public class AddUpdateMovieAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateMovieAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getPathInfo();
        try {
            String originalTitle = validateTitle(req.getParameter(ORIGINAL_TITLE_PARAM));
            LocalTime duration = LocalTime.parse(validateNullOrEmpty(req.getParameter(DURATION_PARAM)));
            Long budget = Long.valueOf(validateLong(req.getParameter(BUDGET_PARAM)));
            LocalDate releaseDate = LocalDate.parse(validateDate(req.getParameter(RELEASE_DATE_PARAM)));
            String description = validateNullOrEmpty(req.getParameter(DESCRIPTION_PARAM));
            Part filePart = req.getPart(IMAGE_PARAM);
            InputStream image = filePart.getInputStream();
            MovieDAO movieDAO = new MovieDAO();
            Movie movie = new Movie();
            movie.setOriginalTitle(originalTitle);
            movie.setDuration(duration);
            movie.setBudget(budget);
            movie.setReleaseDate(releaseDate);
            movie.setDescription(description);
            movie.setImage(image);
            if (ADD_MOVIE_ACTION.equals(action)) {
                movieDAO.create(movie);
            } else if (UPDATE_MOVIE_ACTION.equals(action)) {
                Long movieId = Long.valueOf(validateLong(req.getParameter(MOVIE_ID_PARAM)));
                movie.setId(movieId);
                movieDAO.update(movie);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_MOVIE_ACTION, false);
        return actionResult;
    }
}
