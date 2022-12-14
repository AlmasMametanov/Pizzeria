package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.ProductSize;
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

public class GetAllProductAction implements Action {
    private ProductDAO productDAO = new ProductDAOImpl();
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            List<Product> productList = productDAO.getAllProduct();
            List<AdditionalIngredient> ingredientList = additionalIngredientDAO.getAllAdditionalIngredient();
            List<ProductSize> productSizeList = productSizeDAO.getAllProductSize();
            httpSession.setAttribute(INGREDIENT_LIST, ingredientList);
            httpSession.setAttribute(PRODUCT_SIZE_LIST, productSizeList);
            httpSession.setAttribute(PRODUCT_LIST, productList);
            request.getRequestDispatcher(GET_ALL_PRODUCT_FOR_ADMIN_JSP).forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
