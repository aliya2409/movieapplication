package com.javalab.movieapp.action.admin;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.dao.UserDAO;
import com.javalab.movieapp.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.javalab.movieapp.Constants.*;

public class ListUserAction implements Action {

    private static final Logger LOGGER = Logger.getLogger(ListUserAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.findAll();
            req.setAttribute(USER_LIST_ATTRIB, users);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR_ATTRIB, DATABASE_ERROR);
        }
        ActionResult actionResult = new ActionResult(USER_LIST_PAGE, false);
        return actionResult;
    }
}
