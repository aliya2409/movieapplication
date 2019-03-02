package com.javalab.movieapp.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {

    ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException;
}
