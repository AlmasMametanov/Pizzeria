package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.DeliveryMethodLocaleDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.DeliveryMethodLocaleDAO;
import com.epam.pizzeria.entity.DeliveryMethodLocale;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class CheckoutOrderAction implements Action {
    private DeliveryMethodLocaleDAO deliveryMethodLocaleDAO = new DeliveryMethodLocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            if (request.getParameter(DELIVERY_METHOD_LOCALE_ID) != null) {
                Long deliveryMethodLocaleId = Long.parseLong(request.getParameter(DELIVERY_METHOD_LOCALE_ID));
                DeliveryMethodLocale deliveryMethodLocale = deliveryMethodLocaleDAO.getDeliveryMethodLocaleById(deliveryMethodLocaleId);
                httpSession.setAttribute(DELIVERY_METHOD_LOCALE, deliveryMethodLocale);
            }
            request.getRequestDispatcher(CHECKOUT_ORDER_JSP).forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
