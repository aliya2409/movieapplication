package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.UserService;
import com.javalab.movieapp.utils.security.PasswordEncryptor;
import com.javalab.movieapp.utils.validators.InputValidationException;
import com.javalab.movieapp.utils.validators.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.*;
import static com.javalab.movieapp.utils.validators.InputValidator.validateEmail;
import static com.javalab.movieapp.utils.validators.InputValidator.validateNullOrEmpty;

@Component
public class LogInAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(LogInAction.class);

    @Autowired
    private UserService userService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        ActionResult actionResult = new ActionResult(AUTHORIZATION_PAGE, false);
        try {
            String email = validateEmail(req.getParameter(EMAIL_PARAM));
            String password = PasswordEncryptor.getPasswordEncrypted(validateNullOrEmpty(req.getParameter(PASSWORD_PARAM)));
            UserDto currentUser = userService.findUser(email, password);
            if (UserValidator.isntEmptyUser(currentUser)) {
                currentUser.setPassword(null);
                req.getSession().setAttribute(USER_ATTRIB, currentUser);
                actionResult.setPath(HOME_PAGE);
                actionResult.setRedirect(true);
            } else {
                req.setAttribute(ERROR_ATTRIB, WRONG_PASS_OR_EMAIL_ERROR);
            }
        } catch (InputValidationException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, INCORRECT_INPUT_ERROR);
        }
        return actionResult;
    }
}
