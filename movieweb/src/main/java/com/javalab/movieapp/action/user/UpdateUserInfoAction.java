package com.javalab.movieapp.action.user;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.UserService;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;

@Component
public class UpdateUserInfoAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoAction.class);

    @Autowired
    private UserService userService;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(HOME_PAGE, false);
        try {
            UserDto currentUser = (UserDto) req.getSession().getAttribute(USER_ATTRIB);
            Long userId = Long.valueOf(validateLong(currentUser.getId().toString()));
            String newLogin = validateName(req.getParameter(LOGIN_PARAM));
            LocalDate newBirthDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(BIRTHDATE_PARAM)));
            userService.changeUserInfo(userId, newLogin, newBirthDate);
            currentUser.setLogin(newLogin);
            currentUser.setBirthDate(newBirthDate);
            req.getSession().setAttribute(USER_ATTRIB, currentUser);
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        return actionResult;
    }
}
