package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.UserService;
import com.javalab.movieapp.utils.security.PasswordEncryptor;
import com.javalab.movieapp.utils.validators.InputValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.*;
import static com.javalab.movieapp.utils.validators.UserValidator.isntEmptyUser;

@Component
public class RegisterAction implements Action {

    private static final String DATE_INPUT_FORMAT = "yyyy-MM-d";
    private static final Logger LOGGER = Logger.getLogger(RegisterAction.class);

    @Autowired
    private UserService userService;

    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        UserDto user = new UserDto();
        try {
            user.setLogin(validateName(req.getParameter(LOGIN_PARAM)));
            user.setPassword(PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(PASSWORD_PARAM))));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_INPUT_FORMAT);
            LocalDate localDate = LocalDate.parse(validateNullOrEmpty(req.getParameter(BIRTHDATE_PARAM)), formatter);
            user.setBirthDate(localDate);
            user.setEmail(validateEmail(req.getParameter(EMAIL_PARAM)));
            user.setRoleId(USER_ROLE_ID);
            UserDto foundByEmail = userService.findUserByEmail(user.getEmail());
            if (isntEmptyUser(foundByEmail)) {
                req.setAttribute(ERROR_ATTRIB, EMAIL_IS_ALREADY_TAKEN_ERROR);
            } else {
                userService.create(user);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        ActionResult actionResult = new ActionResult(AUTHORIZATION_PAGE, false);
        return actionResult;
    }
}
