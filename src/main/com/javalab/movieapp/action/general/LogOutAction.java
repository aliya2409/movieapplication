package com.javalab.movieapp.action.general;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.javalab.movieapp.Constants.HOME_PAGE;
import static com.javalab.movieapp.Constants.USER_ATTRIB;

public class LogOutAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(LogOutAction.class);


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {
        req.getSession().setAttribute(USER_ATTRIB, null);
        LOGGER.debug("Log out successful");
        ActionResult actionResult = new ActionResult(HOME_PAGE, true);
        return actionResult;
    }
}
