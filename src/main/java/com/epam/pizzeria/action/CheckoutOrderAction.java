package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.DeliveryMethodLocaleDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.DeliveryMethodLocaleDAO;
import com.epam.pizzeria.entity.Basket;
import com.epam.pizzeria.entity.DeliveryMethodLocale;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.ErrorConstants.*;
import static com.epam.pizzeria.util.constants.ErrorConstants.ERROR_PASSWORD;
import static com.epam.pizzeria.util.constants.LimitConstants.MAX_PRODUCT_COUNT_IN_BASKET;
import static com.epam.pizzeria.util.constants.LimitConstants.MIN_PRODUCT_COUNT_IN_BASKET;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;
import static com.epam.pizzeria.validator.Validator.*;

public class CheckoutOrderAction implements Action {
    private DeliveryMethodLocaleDAO deliveryMethodLocaleDAO = new DeliveryMethodLocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            if (request.getParameter("deliveryMethodLocaleId") != null) {
                Long deliveryMethodLocaleId = Long.parseLong(request.getParameter("deliveryMethodLocaleId"));
                DeliveryMethodLocale deliveryMethodLocale = deliveryMethodLocaleDAO.getDeliveryMethodLocaleById(deliveryMethodLocaleId);
                httpSession.setAttribute("deliveryMethodLocale", deliveryMethodLocale);
            }
            request.getRequestDispatcher("checkoutOrder.jsp").forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
