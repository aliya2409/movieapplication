package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.entity.User;
import com.javalab.movieapp.security.PasswordEncryptor;
import com.javalab.movieapp.validator.InputValidationException;
import com.javalab.movieapp.validator.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateEmail;
import static com.javalab.movieapp.validator.InputValidator.validateNullOrEmpty;

public class LogInAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(LogInAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String nextDestination = AUTHORIZATION_PAGE;
        UserDAO userDAO = new UserDAO();
        try {
            String email = validateEmail(req.getParameter(EMAIL_PARAM));
            String password = PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(PASSWORD_PARAM)));
            User currentUser = userDAO.findUser(email, password);
            if (UserValidator.isntEmptyUser(currentUser)) {
                currentUser.setPassword(null);
                req.getSession().setAttribute(USER_ATTRIB, currentUser);
                nextDestination = HOME_PAGE;
            } else {
                req.setAttribute(ERROR_ATTRIB, WRONG_PASS_OR_EMAIL_ERROR);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        ActionResult actionResult = new ActionResult(nextDestination, false);
        return actionResult;
    }
}
