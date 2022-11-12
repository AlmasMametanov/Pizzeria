package com.epam.pizzeria.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.pizzeria.util.constants.PageNameConstants.PAGE_NOT_FOUND_JSP;

public class PageNotFoundAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(PAGE_NOT_FOUND_JSP);
    }
}
