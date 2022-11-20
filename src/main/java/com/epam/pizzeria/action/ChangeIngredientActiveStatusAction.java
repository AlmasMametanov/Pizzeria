package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeIngredientActiveStatusAction implements Action {
    private ActionFactory actionFactory = ActionFactory.getInstance();
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            Long ingredientId = Long.parseLong(request.getParameter(INGREDIENT_ID));
            AdditionalIngredient additionalIngredient = additionalIngredientDAO.getAdditionalIngredientById(ingredientId);
            additionalIngredient.setIsActive(!additionalIngredient.getIsActive());
            additionalIngredientDAO.updateAdditionalIngredientActiveStatus(additionalIngredient);
            actionFactory.getAction(GET_ALL_ADDITIONAL_INGREDIENT_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
