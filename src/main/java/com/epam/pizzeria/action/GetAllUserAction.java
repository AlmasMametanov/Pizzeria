package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class GetAllUserAction implements Action {
    private UserDAO userDao = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            List<User> users = userDao.getAllUsers();
            if (!users.isEmpty()) {
                request.setAttribute(USERS, users);
                request.getRequestDispatcher(GET_ALL_USER_JSP).forward(request, response);
            } else {
                response.sendRedirect(ADMIN_PANEL_JSP);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }

    }
}
