package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeIngredientDetailDAO;
import com.epam.pizzeria.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeIngredientAction implements Action {
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();
    private ProductSizeIngredientDetailDAO productSizeIngredientDetailDAO = new ProductSizeIngredientDetailDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            Long ingredientId = Long.parseLong(request.getParameter(INGREDIENT_ID));
            AdditionalIngredient additionalIngredient = additionalIngredientDAO.getAdditionalIngredientById(ingredientId);
            setParametersToAdditionalIngredientAndUpdateInDatabase(additionalIngredient, request);
            setParametersToProductSizeIngredientDetailAndUpdateInDatabase(httpSession, request);
            actionFactory.getAction(GET_ALL_ADDITIONAL_INGREDIENT_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void setParametersToAdditionalIngredientAndUpdateInDatabase(AdditionalIngredient additionalIngredient, HttpServletRequest request) {
        if (request.getParameter(IMAGE_URL) != "")
            additionalIngredient.setImageUrl(request.getParameter(IMAGE_URL));
        additionalIngredient.setName(request.getParameter(INGREDIENT_NAME));
        additionalIngredientDAO.updateAdditionalIngredient(additionalIngredient);
    }

    private void setParametersToProductSizeIngredientDetailAndUpdateInDatabase(HttpSession httpSession, HttpServletRequest request) {
        List<AdditionalIngredient> additionalIngredientList = (List<AdditionalIngredient>) httpSession.getAttribute(ADDITIONAL_INGREDIENT_LIST);
        Integer ingredientLoopIndex = Integer.parseInt(request.getParameter(INGREDIENT_LOOP_INDEX));
        List<String> productSizeIngredientDetailList = Arrays.asList(request.getParameterValues(INGREDIENT_SIZE_PRICE));
        AdditionalIngredient additionalIngredient = additionalIngredientList.get(ingredientLoopIndex);
        for (int i = 0; i < additionalIngredient.getProductSizeList().size(); i++) {
            Integer newPrice = Integer.valueOf(productSizeIngredientDetailList.get(i));
            Long productSizeIngredientDetailId = additionalIngredient.getProductSizeList().get(i).getProductSizeIngredientDetail().getId();
            productSizeIngredientDetailDAO.updateProductSizeIngredientPrice(productSizeIngredientDetailId, newPrice);
        }
    }
}
