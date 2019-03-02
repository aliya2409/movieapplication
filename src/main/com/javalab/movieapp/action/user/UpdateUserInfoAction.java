package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.entity.User;
import com.javalab.movieapp.validator.InputValidationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.validator.InputValidator.*;

public class UpdateUserInfoAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(HOME_PAGE, false);
        try {
            User currentUser = (User) req.getSession().getAttribute(USER_ATTRIB);
            Long userId = Long.valueOf(validateLong(currentUser.getId().toString()));
            String newLogin = validateName(req.getParameter(LOGIN_PARAM));
            LocalDate newBirthDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(BIRTHDATE_PARAM)));
            UserDAO userDAO = new UserDAO();
            userDAO.changeUserInfo(userId, newLogin, newBirthDate);
            currentUser.setLogin(newLogin);
            currentUser.setBirthDate(newBirthDate);
            req.getSession().setAttribute(USER_ATTRIB, currentUser);
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
