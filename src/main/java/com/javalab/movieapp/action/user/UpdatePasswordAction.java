package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.entities.User;
import com.javalab.movieapp.utils.security.PasswordEncryptor;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateLong;
import static com.javalab.movieapp.utils.validators.InputValidator.validateNullOrEmpty;

public class UpdatePasswordAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(UpdatePasswordAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(HOME_PAGE, true);
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
                    userDAO.changePassword(userId, newPassword);
                }
            } else {
                throw new InputValidationException();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
            actionResult.setRedirect(false);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
            actionResult.setRedirect(false);
        }
        return actionResult;
    }
}
