package com.javalab.movieapp;

import com.javalab.movieapp.action.Action;
import com.javalab.movieapp.action.ActionFactory;
import com.javalab.movieapp.action.ActionResult;
import com.javalab.movieapp.entities.Language;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javalab.movieapp.Constants.*;

public class MovieAppServlet extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(MovieAppServlet.class);
    private static final String JSP_LOCATION = "/WEB-INF/jsp";
    private final ActionFactory actionFactory = new ActionFactory();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        Long languageId = (Long) req.getSession().getAttribute(LANGUAGE_ID_ATTRIB);
        if (languageId == null) {
            languageId = DEFAULT_LANGUAGE_ID;
            req.getSession().setAttribute(LANGUAGE_ID_ATTRIB, languageId);
            String locale = Language.getLanguageIdToLocale().get(languageId.toString());
            req.getSession().setAttribute(LOCALE_ATTRIB, locale);
        }
        ActionResult actionResult = new ActionResult(HOME_PAGE, true);
        String requestedPath = req.getPathInfo();
        if (requestedPath.startsWith(JSP_LOCATION) || requestedPath.equals(HOME_PAGE)) {
            doGet(req, resp);
        } else {
            Action currentAction = actionFactory.getAction(requestedPath);
            if (currentAction != null) {
                actionResult = currentAction.execute(req, resp);
            } else {
                LOGGER.error(ACTION_NOT_FOUND_ERROR);
                req.setAttribute(ERROR_ATTRIB, ACTION_NOT_FOUND_ERROR);}
            if (actionResult.isRedirect()) {
                String finalPath = req.getContextPath() + req.getServletPath() +actionResult.getPath();
                resp.sendRedirect(finalPath);
            } else {
                req.getRequestDispatcher(actionResult.getPath()).forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(req.getPathInfo()).forward(req, res);
    }
}
