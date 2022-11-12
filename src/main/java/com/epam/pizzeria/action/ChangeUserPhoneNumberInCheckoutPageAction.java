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
import static com.epam.pizzeria.util.constants.ErrorConstants.ERROR_PHONE_NUMBER;
import static com.epam.pizzeria.util.constants.ErrorConstants.PHONE_FORMAT_INCORRECT;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;
import static com.epam.pizzeria.validator.Validator.validatePhoneNumber;

public class ChangeUserPhoneNumberInCheckoutPageAction implements Action {
    private UserDAO userDAO = new UserDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            if (!user.getPhoneNumber().equals(request.getParameter("phoneNumber"))) {
                Boolean isValidatorPassed = validators(request);
                if (isValidatorPassed) {
                    String phoneNumber = request.getParameter("phoneNumber");
                    user.setPhoneNumber(phoneNumber);
                    userDAO.updateUserPhoneNumber(user);
                }
                actionFactory.getAction(CHECKOUT_ORDER_ACTION).execute(request, response);
            } else {
                actionFactory.getAction(CHECKOUT_ORDER_ACTION).execute(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private Boolean validators(HttpServletRequest request) {
        Boolean isValidatorPassed = true;
        if (!validatePhoneNumber(request.getParameter("phoneNumber"))) {
            request.setAttribute(PHONE_FORMAT_INCORRECT, ERROR_PHONE_NUMBER);
            isValidatorPassed = false;
        }
        if (!isValidatorPassed)
            return false;
        return true;
    }
}
