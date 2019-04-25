package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dto.UserDto;
import com.javalab.movieapp.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

@Component
public class ListUserAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListUserAction.class);

    @Autowired
    private UserService userService;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) {

        List<UserDto> users = userService.findAll();
        req.setAttribute(USER_LIST_ATTRIB, users);

        ActionResult actionResult = new ActionResult(USER_LIST_PAGE, false);
        return actionResult;
    }
}
