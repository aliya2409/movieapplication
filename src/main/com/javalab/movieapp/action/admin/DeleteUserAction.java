package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;

public class DeleteUserAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(DeleteUserAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(LIST_USER_ACTION, false);
        try {
            Long userId = Long.valueOf(validateLong(req.getParameter(USER_ID_PARAM)));
            UserDAO userDAO = new UserDAO();
            userDAO.delete(userId);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        return actionResult;
    }
}
