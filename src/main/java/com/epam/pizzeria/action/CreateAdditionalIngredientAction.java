package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeIngredientDetailDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.ProductSize;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class CreateAdditionalIngredientAction implements Action {
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
    private ProductSizeIngredientDetailDAO productSizeIngredientDetailDAO = new ProductSizeIngredientDetailDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            if (request.getParameter(INGREDIENT_NAME) != null) {
                List<Integer> ingredientPriceBySizeList = getIngredientPriceList(request);
                List<Long> sizeIdList = getSizeIdList(request);
                AdditionalIngredient additionalIngredient = new AdditionalIngredient();
                setParametersToAdditionalIngredientsAndInsertIntoDatabase(request, additionalIngredient);
                setParametersToProductSizeIngredientDetailAndInsertIntoDatabase(ingredientPriceBySizeList, sizeIdList, additionalIngredient);
                request.getRequestDispatcher(INDEX_JSP).forward(request, response);
            } else {
                List<ProductSize> productSizeList = productSizeDAO.getAllProductSize();
                httpSession.setAttribute(PRODUCT_SIZE_LIST, productSizeList);
                request.getRequestDispatcher(CREATE_ADDITIONAL_INGREDIENT_JSP).forward(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private List<Long> getSizeIdList(HttpServletRequest request) {
        List<String> stringSizeIdList = Arrays.asList(request.getParameterValues(PRODUCT_SIZE_ID));
        return stringSizeIdList.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    private List<Integer> getIngredientPriceList(HttpServletRequest request) {
        List<String> stringIngredientPriceBySizeList = Arrays.asList(request.getParameterValues(INGREDIENT_PRICE));
        return stringIngredientPriceBySizeList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    private AdditionalIngredient setParametersToAdditionalIngredientsAndInsertIntoDatabase(HttpServletRequest request, AdditionalIngredient additionalIngredient) {
        String ingredientName = request.getParameter(INGREDIENT_NAME);
        String imageUrl = request.getParameter(IMAGE_URL);
        additionalIngredient.setName(ingredientName);
        additionalIngredient.setImageUrl(imageUrl);
        additionalIngredientDAO.insertAdditionalIngredient(additionalIngredient);
        return additionalIngredient;
    }


    private void setParametersToProductSizeIngredientDetailAndInsertIntoDatabase(List<Integer> ingredientPriceBySizeList, List<Long> sizeIdList, AdditionalIngredient additionalIngredient) {
        ProductSizeIngredientDetail productSizeIngredientDetail = null;
        for (int i = 0; i < ingredientPriceBySizeList.size(); i++) {
            productSizeIngredientDetail = new ProductSizeIngredientDetail();
            productSizeIngredientDetail.setSizeId(sizeIdList.get(i));
            productSizeIngredientDetail.setIngredientId(additionalIngredient.getId());
            productSizeIngredientDetail.setPrice(ingredientPriceBySizeList.get(i));
            productSizeIngredientDetailDAO.insertProductSizeIngredientDetail(productSizeIngredientDetail);
        }
    }
}
