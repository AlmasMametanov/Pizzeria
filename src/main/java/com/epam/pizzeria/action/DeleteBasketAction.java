package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.BasketDAOImpl;
import com.epam.pizzeria.database.dao.impl.BasketIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.BasketDAO;
import com.epam.pizzeria.database.dao.interfaces.BasketIngredientDetailDAO;
import com.epam.pizzeria.entity.Basket;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class DeleteBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private BasketIngredientDetailDAO basketIngredientDetailDAO = new BasketIngredientDetailDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            List<Basket> baskets = (List<Basket>) httpSession.getAttribute(BASKETS_BY_USER);
            Long basketId = Long.parseLong(request.getParameter(BASKET_ID));
            int basketLoopIndex = Integer.parseInt(request.getParameter(BASKET_LOOP_INDEX));
            Integer totalPrice = (Integer) httpSession.getAttribute(TOTAL_PRICE);
            totalPrice -= baskets.get(basketLoopIndex).getProduct().getPrice();
            basketIngredientDetailDAO.deleteBasketIngredientDetailByBasketId(basketId);
            basketDAO.deleteBasketById(basketId);
            baskets.remove(basketLoopIndex);
            httpSession.setAttribute(TOTAL_PRICE, totalPrice);
            httpSession.setAttribute(BASKETS_BY_USER, baskets);
            actionFactory.getAction(GET_BASKET_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
