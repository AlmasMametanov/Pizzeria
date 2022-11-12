package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.UserDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.CHECKOUT_ORDER_ACTION;
import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeUserAddressInCheckoutPageAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            String address = request.getParameter("address");
            user.setAddress(address);
            userDAO.updateUserAddress(user);
            actionFactory.getAction(CHECKOUT_ORDER_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
