package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.BasketDAOImpl;
import com.epam.pizzeria.database.dao.impl.BasketIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.BasketDAO;
import com.epam.pizzeria.database.dao.interfaces.BasketIngredientDetailDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductDAO;
import com.epam.pizzeria.entity.Basket;
import com.epam.pizzeria.entity.BasketIngredientDetail;
import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class CreateBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private BasketIngredientDetailDAO basketIngredientDetailDAO = new BasketIngredientDetailDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
            Product product = productDAO.getProductById(productId);
            if (product.getIsPizza()) {
                ifProductIsPizza(request, user, productId);
            } else {
                ifProductIsNotPizza(user, productId);
            }
            response.sendRedirect(INDEX_JSP);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void ifProductIsNotPizza(User user, Long productId) {
        Basket basketFromDB = basketDAO.getProductIfProductIsNotPizza(user.getId(), productId);
        if (basketFromDB != null) {
            basketDAO.incrementProductCountPerUnitInBasket(basketFromDB.getId());
        } else {
            Basket basket = new Basket();
            setParametersToBasketForNotPizzaAndInsertIntoDatabase(basket, productId, user);
        }
    }

    private void ifProductIsPizza(HttpServletRequest request, User user, Long productId) {
        Long productSizeId = Long.parseLong(request.getParameter(PRODUCT_SIZE_ID));
        List<Basket> basketListFromDB = basketDAO.getProductsIfProductIsPizza(user.getId(), productId, productSizeId);
        List<Long> ingredientIdList = ifIngredientsSelected(request);
        if (basketListFromDB.isEmpty()) {
            createBasketIfProductIsPizza(productId, productSizeId, user, ingredientIdList);
        } else {
            Boolean ifIngredientsExistsAnywhere = checkIfBasketExistInDB(basketListFromDB, ingredientIdList);
            if (!ifIngredientsExistsAnywhere)
                createBasketIfProductIsPizza(productId, productSizeId, user, ingredientIdList);
        }
    }

    private void setParametersToBasketIngredientDetailAndInsertIntoDatabase(Basket basket, Long additionalIngredientDetailId) {
        BasketIngredientDetail basketIngredientDetail = new BasketIngredientDetail();
        basketIngredientDetail.setBasketId(basket.getId());
        basketIngredientDetail.setAdditionalIngredientDetailId(additionalIngredientDetailId);
        basketIngredientDetailDAO.insertBasketIngredientDetail(basketIngredientDetail);
    }

    private List<Long> ifIngredientsSelected(HttpServletRequest request) {
        String isIngredientsSelected = request.getParameter(SELECTED_INGREDIENT_ID);
        if (isIngredientsSelected != null && !isIngredientsSelected.isEmpty()) {
            List<String> stringIngredientIdList = Arrays.asList(request.getParameterValues(SELECTED_INGREDIENT_ID));
            return stringIngredientIdList.stream().map(Long::parseLong).collect(Collectors.toList());
        }
        return null;
    }

    private void setParametersToBasketForPizzaAndInsertIntoDatabase(Basket basket, Long productId, Long productSizeId, User user) {
        basket.setProductId(productId);
        basket.setProductSizeId(productSizeId);
        basket.setUserId(user.getId());
        basketDAO.insertBasketForPizza(basket);
    }

    private Boolean checkIfBasketExistInDB(List<Basket> basketListFromDB, List<Long> newBasketIngredientIdList) {
        Boolean ifIngredientsExistAnywhere = false;
        for (Basket basket : basketListFromDB) {
            List<Long> ingredientIdListByBasketIdFromDB = basketIngredientDetailDAO.getIngredientIdListByBasketId(basket.getId());
            if (ingredientIdListByBasketIdFromDB.isEmpty() && newBasketIngredientIdList == null) {
                basketDAO.incrementProductCountPerUnitInBasket(basket.getId());
                ifIngredientsExistAnywhere = true;
                break;
            } else if (!ingredientIdListByBasketIdFromDB.isEmpty() && newBasketIngredientIdList != null){
                Boolean isIngredientExistInBasket = checkEachIngredientIdFromList(newBasketIngredientIdList, ingredientIdListByBasketIdFromDB);
                if (isIngredientExistInBasket) {
                    basketDAO.incrementProductCountPerUnitInBasket(basket.getId());
                    ifIngredientsExistAnywhere = true;
                    break;
                }
            }
        }
        return ifIngredientsExistAnywhere;
    }

    private void setParametersToBasketForNotPizzaAndInsertIntoDatabase(Basket basket, Long productId, User user) {
        basket.setProductId(productId);
        basket.setUserId(user.getId());
        basketDAO.insertBasketForNotPizza(basket);
    }

    private Boolean checkEachIngredientIdFromList(List<Long> newBasketIngredientIdList, List<Long> ingredientIdListByBasketIdFromDB) {
        if (newBasketIngredientIdList.size() == ingredientIdListByBasketIdFromDB.size()) {
            Collections.sort(newBasketIngredientIdList);
            Collections.sort(ingredientIdListByBasketIdFromDB);
            return newBasketIngredientIdList.equals(ingredientIdListByBasketIdFromDB);
        }
        return false;
    }

    private void createBasketIfProductIsPizza(Long productId, Long productSizeId, User user, List<Long> ingredientDetailIdList) {
        Basket basket = new Basket();
        setParametersToBasketForPizzaAndInsertIntoDatabase(basket, productId, productSizeId, user);
        if (ingredientDetailIdList != null) {
            for (Long ingredientDetailId : ingredientDetailIdList) {
                setParametersToBasketIngredientDetailAndInsertIntoDatabase(basket, ingredientDetailId);
            }
        }
    }
}
