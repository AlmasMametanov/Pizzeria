package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class GetUserByIdAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkIfUserIsAdmin = (User) httpSession.getAttribute(USER);
        if (checkIfUserIsAdmin != null && checkIfUserIsAdmin.getIsAdmin()) {
            Long userId = null;
            if (request.getParameter(USER_ID) == null) {
                userId = (Long) httpSession.getAttribute(USER_ID_FOR_ADMIN);
            } else {
                userId = Long.valueOf(request.getParameter(USER_ID));
                httpSession.setAttribute(USER_ID_FOR_ADMIN, userId);
            }
            User user = userDAO.getUserById(userId);
            if (user != null) {
                httpSession.setAttribute(USER_DATA_FOR_ADMIN, user);
                request.getRequestDispatcher(GET_USER_DATA_BY_ID_FOR_ADMIN_JSP).forward(request, response);
            } else {
                response.sendRedirect(ADMIN_PANEL_JSP);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
