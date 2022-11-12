package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.GET_USER_BY_ID_ACTION;
import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeUserBanStatusAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkIfUserIsAdmin = (User) httpSession.getAttribute(USER);
        if (checkIfUserIsAdmin != null && checkIfUserIsAdmin.getIsAdmin()) {
            Long userId = Long.valueOf(request.getParameter(USER_ID));
            User user = userDAO.getUserById(userId);
            user.setIsBanned(!user.getIsBanned());
            userDAO.updateUserBanStatus(user);
            actionFactory.getAction(GET_USER_BY_ID_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
