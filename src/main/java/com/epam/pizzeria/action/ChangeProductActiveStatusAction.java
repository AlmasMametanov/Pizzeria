package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.ProductDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.ProductDAO;
import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeProductActiveStatusAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            Long productId = Long.valueOf(request.getParameter(PRODUCT_ID));
            Product product = productDAO.getProductById(productId);
            product.setIsActive(!product.getIsActive());
            productDAO.updateProductActiveStatus(product);
            request.getRequestDispatcher(GET_ALL_PRODUCT_ACTION).forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
