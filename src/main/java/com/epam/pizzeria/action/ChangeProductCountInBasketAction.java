package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.BasketDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.BasketDAO;
import com.epam.pizzeria.entity.Basket;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.GET_BASKET_ACTION;
import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeProductCountInBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            List<Basket> baskets = (List<Basket>) httpSession.getAttribute("basketsByUser");
            Long basketId = Long.parseLong(request.getParameter("basketId"));
            Integer totalPrice = (Integer) httpSession.getAttribute("totalPrice");
            Integer basketLoopIndex = Integer.parseInt(request.getParameter("basketLoopIndex"));
            Integer newProductCount = Integer.parseInt(request.getParameter("newProductCount"));
            if (!baskets.get(basketLoopIndex).getCount().equals(newProductCount)) {
                Integer priceOfOneProduct = baskets.get(basketLoopIndex).getProduct().getPrice() / baskets.get(basketLoopIndex).getCount();
                Integer newPrice = priceOfOneProduct * newProductCount;
                totalPrice -= priceOfOneProduct * baskets.get(basketLoopIndex).getCount();
                totalPrice += newPrice;
                baskets.get(basketLoopIndex).getProduct().setPrice(newPrice);
                baskets.get(basketLoopIndex).setCount(newProductCount);
                basketDAO.updateProductCountInBasket(newProductCount, basketId);
            }
            httpSession.setAttribute("basketsByUser", baskets);
            httpSession.setAttribute("totalPrice", totalPrice);
            actionFactory.getAction(GET_BASKET_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
