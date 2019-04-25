package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.GenreDto;
import com.javalab.movieapp.dto.MovieDto;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.GenreService;
import com.javalab.movieapp.services.MovieService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

@Component
public class ListLikedMovies implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListLikedMovies.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            UserDto currentUser = (UserDto) req.getSession().getAttribute(USER_ATTRIB);
            Long userId = Long.valueOf(validateLong(currentUser.getId().toString()));
            Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
            List<MovieDto> likedMovies = movieService.findLikedMovies(userId, languageId);
            for (MovieDto movie : likedMovies) {
                List<GenreDto> genres = genreService.findMovieGenres(movie.getId(), languageId);
                movie.setGenres(genres);
            }
            req.setAttribute(MOVIE_LIST_ATTRIB, likedMovies);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(MOVIE_LIST_PAGE, false);
        return actionResult;
    }
}
