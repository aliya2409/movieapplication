package com.javalab.movieapp.action.admin;

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
import java.time.LocalDate;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;
import static com.javalab.movieapp.utils.validators.UserValidator.isntEmptyUser;

public class AddUpdateUserAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(AddUpdateUserAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        String action = req.getPathInfo();
        try {

            String login = validateName(req.getParameter(LOGIN_PARAM));
            String password = validateNullOrEmpty(req.getParameter(PASSWORD_PARAM));
            Integer roleId = Integer.valueOf(validateInteger(req.getParameter(ROLE_ID_PARAM)));
            String mail = validateEmail(req.getParameter(EMAIL_PARAM));
            LocalDate birthDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(BIRTHDATE_PARAM)));
            User user = new User();
            user.setLogin(login);
            user.setPassword(PasswordEncryptor.getPasswordEncrypted(password));
            user.setRoleId(roleId);
            user.setEmail(mail);
            user.setBirthDate(birthDate);
            UserDAO userDAO = new UserDAO();
            if (UPDATE_USER_ACTION.equals(action)) {
                Long userId = Long.valueOf(validateLong(req.getParameter(USER_ID_PARAM)));
                user.setId(userId);
                userDAO.update(user);
            } else if (ADD_USER_ACTION.equals(action)) {
                User foundByEmail = userDAO.findUserByEmail(user.getEmail());
                if (isntEmptyUser(foundByEmail)) {
                    req.setAttribute(ERROR_ATTRIB, EMAIL_IS_ALREADY_TAKEN_ERROR);
                } else {
                    userDAO.create(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(LIST_USER_ACTION, false);
        return actionResult;
    }
}
