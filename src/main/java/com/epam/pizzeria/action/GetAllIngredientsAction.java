package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
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

public class GetAllIngredientsAction implements Action {
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            List<AdditionalIngredient> additionalIngredientList = additionalIngredientDAO.getAllAdditionalIngredient();
            getAllProductSizeIngredient(additionalIngredientList);
            httpSession.setAttribute(ADDITIONAL_INGREDIENT_LIST, additionalIngredientList);
            request.getRequestDispatcher(GET_ALL_ADDITIONAL_INGREDIENT_JSP).forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void getAllProductSizeIngredient(List<AdditionalIngredient> additionalIngredientList) {
        for (AdditionalIngredient additionalIngredient : additionalIngredientList) {
            additionalIngredient.setProductSizeList(productSizeDAO.getAllProductSizeByIngredientId(additionalIngredient.getId()));
        }
    }
}
