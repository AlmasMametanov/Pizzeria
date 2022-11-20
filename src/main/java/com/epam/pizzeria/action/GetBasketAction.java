package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.*;
import com.epam.pizzeria.database.dao.interfaces.*;
import com.epam.pizzeria.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;
import static com.epam.pizzeria.util.constants.LimitConstants.*;

public class GetBasketAction implements Action {
    private BasketDAO basketDAO = new BasketDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
    private BasketIngredientDetailDAO basketIngredientDetailDAO = new BasketIngredientDetailDAOImpl();
    private DeliveryMethodLocaleDAO deliveryMethodLocaleDAO = new DeliveryMethodLocaleDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
            List<Basket> baskets = basketDAO.getAllBasketsByUserId(user.getId());
            Product productFromBasket = null;
            Integer totalPrice = 0;
            for (Basket basket : baskets) {
                productFromBasket = productDAO.getProductById(basket.getProductId());
                if (productFromBasket.getIsPizza())
                    ifProductIsPizza(basket, productFromBasket);
                if (basket.getCount() > 1)
                    productFromBasket.setPrice(basket.getCount() * productFromBasket.getPrice());
                totalPrice += productFromBasket.getPrice();
                basket.setProduct(productFromBasket);
            }
            List<DeliveryMethodLocale> deliveryMethodLocaleList = deliveryMethodLocaleDAO.getAllDeliveryMethodLocaleByLocaleId(localeId);
            setAttributes(httpSession, totalPrice, baskets, deliveryMethodLocaleList);
            request.getRequestDispatcher(BASKET_JSP).forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void setAttributes(HttpSession httpSession, Integer totalPrice, List<Basket> baskets, List<DeliveryMethodLocale> deliveryMethodLocaleList) {
        httpSession.setAttribute(DELIVERY_METHOD_LOCALE_LIST, deliveryMethodLocaleList);
        httpSession.setAttribute(TOTAL_PRICE, totalPrice);
        httpSession.setAttribute(BASKETS_BY_USER, baskets);
        httpSession.setAttribute(MIN_PRODUCT_COUNT_IN_BASKET_PARAMETER, MIN_PRODUCT_COUNT_IN_BASKET);
        httpSession.setAttribute(MAX_PRODUCT_COUNT_IN_BASKET_PARAMETER, MAX_PRODUCT_COUNT_IN_BASKET);
    }

    private Integer getTotalPriceOfAdditionalIngredients(List<BasketIngredientDetail> basketIngredientDetailList) {
        Integer totalPriceOfAdditionalIngredients = 0;
        for (BasketIngredientDetail basketIngredientDetail : basketIngredientDetailList) {
            totalPriceOfAdditionalIngredients = totalPriceOfAdditionalIngredients + basketIngredientDetail.getProductSizeIngredientDetail().getPrice();
        }
        return totalPriceOfAdditionalIngredients;
    }

    private void ifProductIsPizza(Basket basket, Product productFromBasket) {
        ProductSize productSize = productSizeDAO.getProductSizeBySizeIdAndProductId(basket.getProductSizeId(), basket.getProductId());
        List<BasketIngredientDetail> basketIngredientDetailList = basketIngredientDetailDAO.getBasketIngredientDetailById(basket.getId());
        Integer totalPriceOfAdditionalIngredients = 0;
        if (!basketIngredientDetailList.isEmpty())
            totalPriceOfAdditionalIngredients = getTotalPriceOfAdditionalIngredients(basketIngredientDetailList);
        Integer productPrice = productSize.getProductSizeDetail().getPrice() + totalPriceOfAdditionalIngredients;
        productFromBasket.setPrice(productPrice);
        productFromBasket.setProductSize(productSize);
        productFromBasket.setBasketIngredientDetailList(basketIngredientDetailList);
    }
}
