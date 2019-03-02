package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.entity.User;
import com.javalab.movieapp.security.PasswordEncryptor;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.validateLong;
import static com.javalab.movieapp.validator.InputValidator.validateNullOrEmpty;

public class UpdatePasswordAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(UpdatePasswordAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        try {
            User currentUser = (User) req.getSession().getAttribute(USER_ATTRIB);
            Long userId = Long.valueOf(validateLong(currentUser.getId().toString()));
            String currentPassword = PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(PASSWORD_PARAM)));
            String newPassword = PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(NEW_PASSWORD_PARAM)));
            String confirmPassword = PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(CONFIRM_PASSWORD_PARAM)));
            if (newPassword.equals(confirmPassword)) {
                UserDAO userDAO = new UserDAO();
                User targetUser;
                targetUser = userDAO.findEntityById(userId);
                if (targetUser.getPassword().equals(currentPassword)) {
                    try {
                        userDAO.changePassword(userId, newPassword);
                    } catch (SQLException e) {
                        throw e;
                    }
                }
            } else {
                throw new InputValidationException();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(HOME_PAGE, false);
        return actionResult;
    }
}
