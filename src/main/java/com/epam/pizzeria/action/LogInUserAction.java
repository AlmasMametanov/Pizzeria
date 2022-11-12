package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;
import com.epam.pizzeria.util.encodePassword.Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class LogInUserAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkUser = (User) httpSession.getAttribute(USER);
        if (checkUser == null) {
            String email = request.getParameter(EMAIL);
            if (email != null) {
                String encodePassword = Encoder.encodePassword(request.getParameter(PASSWORD));
                User user = userDAO.getUserByEmailPassword(email, encodePassword);
                if (user != null && user.getIsBanned() == false) {
                    httpSession.setAttribute(USER, user);
                    request.getRequestDispatcher(INDEX_JSP).forward(request, response);
                } else {
                    request.getRequestDispatcher(INDEX_JSP).forward(request, response);
                }
            } else {
                response.sendRedirect(PAGE_NOT_FOUND_ACTION);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
