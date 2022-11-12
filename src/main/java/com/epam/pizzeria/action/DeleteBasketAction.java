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

import static com.epam.pizzeria.action.ActionConstants.GET_BASKET_ACTION;
import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.PageNameConstants.BASKET_JSP;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.USER;

public class DeleteBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private BasketIngredientDetailDAO basketIngredientDetailDAO = new BasketIngredientDetailDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            List<Basket> baskets = (List<Basket>) httpSession.getAttribute("basketsByUser");
            Long basketId = Long.parseLong(request.getParameter("basketId"));
            int basketLoopIndex = Integer.parseInt(request.getParameter("basketLoopIndex"));
            Integer totalPrice = (Integer) httpSession.getAttribute("totalPrice");
            totalPrice -= baskets.get(basketLoopIndex).getProduct().getPrice();
            basketIngredientDetailDAO.deleteBasketIngredientDetailByBasketId(basketId);
            basketDAO.deleteBasketById(basketId);
            baskets.remove(basketLoopIndex);
            httpSession.setAttribute("totalPrice", totalPrice);
            httpSession.setAttribute("basketsByUser", baskets);
            actionFactory.getAction(GET_BASKET_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
