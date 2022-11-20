package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.*;
import com.epam.pizzeria.database.dao.interfaces.*;
import com.epam.pizzeria.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.LimitConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class CreateProductAction implements Action {
    private ProductCategoryLocaleDAO productCategoryLocaleDAO = new ProductCategoryLocaleDAOImpl();
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();
    private ProductIngredientDetailDAO productIngredientDetailDAO = new ProductIngredientDetailDAOImpl();
    private ProductSizeDetailDAO productSizeDetailDAO = new ProductSizeDetailDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            String productName = request.getParameter(PRODUCT_NAME);
            if (productName != null) {
                Boolean isPizza = Boolean.parseBoolean(request.getParameter(IS_PIZZA));
                createProduct(request, isPizza, httpSession);
                request.getRequestDispatcher(INDEX_JSP).forward(request, response);
            } else {
                int isPizzaInt = Integer.parseInt(request.getParameter(IS_PIZZA));
                Boolean isPizza = false;
                if (isPizzaInt == 1) {
                    isPizza = true;
                    setAttributesIfProductIsPizza(request, httpSession);
                }
                setBasicAttributes(httpSession, request, isPizza);
                request.getRequestDispatcher(CREATE_NEW_PRODUCT_JSP).forward(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void createProduct(HttpServletRequest request, Boolean isPizza, HttpSession httpSession) {
        Product product = new Product();
        if (isPizza) {
            ifProductIsPizza(request, product, isPizza);
            ifProductHasDifferentProductSize(request, httpSession, product);
        } else {
            ifProductIsNotPizza(request, isPizza);
        }
    }

    private void ifProductIsPizza(HttpServletRequest request, Product product, Boolean isPizza) {
        List<Long> ingredientsIdForPizza = getIngredientsIdForPizza(request);
        setValuesIntoProductAndInsertIntoDatabaseIfPizza(request, product, isPizza, ingredientsIdForPizza);
        setValuesIntoProductIngredientDetailAndInsertIntoDatabase(ingredientsIdForPizza, product);
    }


    private void ifProductHasDifferentProductSize(HttpServletRequest request, HttpSession httpSession, Product product) {
        List<Integer> productSizePriceList = getProductSizePriceList(request);
        List<ProductSize> productSizeList = (List<ProductSize>) httpSession.getAttribute(PRODUCT_SIZE_LIST);
        setValuesIntoProductSizeDetailAndInsertIntoDatabase(productSizeList, productSizePriceList, product);

    }

    private void setValuesIntoProductSizeDetailAndInsertIntoDatabase(List<ProductSize> productSizeList, List<Integer> productSizePriceList, Product product) {
        ProductSizeDetail productSizeDetail = null;
        for (int i = 0; i < productSizeList.size(); i++) {
            productSizeDetail = new ProductSizeDetail();
            productSizeDetail.setProductId(product.getId());
            productSizeDetail.setProductSizeId(productSizeList.get(i).getId());
            productSizeDetail.setPrice(productSizePriceList.get(i));
            productSizeDetailDAO.insertPizzaSizeDetail(productSizeDetail);
        }
    }

    private void setValuesIntoProductIngredientDetailAndInsertIntoDatabase(List<Long> ingredientsIdForPizza, Product product) {
        ProductIngredientDetail productIngredientDetail = null;
        for (Long ingredientId : ingredientsIdForPizza) {
            productIngredientDetail = new ProductIngredientDetail();
            productIngredientDetail.setProductId(product.getId());
            productIngredientDetail.setIngredientId(ingredientId);
            productIngredientDetailDAO.insertProductIngredientDetail(productIngredientDetail);
        }
    }

    private void setValuesIntoProductAndInsertIntoDatabaseIfPizza(HttpServletRequest request, Product product, Boolean isPizza, List<Long> ingredientsIdForPizza) {
        product.setName(request.getParameter(PRODUCT_NAME));
        product.setImageUrl(request.getParameter(IMAGE_URL));
        String description = concatenateIngredientsToCreatePizzaDescription(ingredientsIdForPizza);
        product.setDescription(description);
        product.setProductCategoryId(Long.parseLong(request.getParameter(PRODUCT_CATEGORY_ID)));
        product.setIsPizza(isPizza);
        product.setPrice(0);
        productDAO.insertProduct(product);
    }

    private String concatenateIngredientsToCreatePizzaDescription(List<Long> ingredientsIdForPizza) {
        List<String> ingredientNames = new ArrayList<>();
        for (Long ingredientId : ingredientsIdForPizza) {
            ingredientNames.add(additionalIngredientDAO.getAdditionalIngredientNameById(ingredientId));
        }
        return String.join(", ", ingredientNames);
    }

    private List<Integer> getProductSizePriceList(HttpServletRequest request) {
        List<String> stringProductSizePriceList = Arrays.asList(request.getParameterValues(PRODUCT_SIZE_PRICE));
        List<Integer> productSizePriceList = stringProductSizePriceList.stream().map(Integer::parseInt).collect(Collectors.toList());
        return productSizePriceList;
    }

    private List<Long> getIngredientsIdForPizza(HttpServletRequest request) {
        List<String> stringIngredientsIdForPizza = Arrays.asList(request.getParameterValues(SELECTED_INGREDIENT_ID));
        List<Long> ingredientsIdForPizza = stringIngredientsIdForPizza.stream().map(Long::parseLong).collect(Collectors.toList());
        return ingredientsIdForPizza;
    }

    private void ifProductIsNotPizza(HttpServletRequest request, Boolean isPizza) {
        Product product = new Product();
        product.setName(request.getParameter(PRODUCT_NAME));
        product.setDescription(request.getParameter(PRODUCT_DESCRIPTION));
        product.setImageUrl(request.getParameter(IMAGE_URL));
        product.setProductCategoryId(Long.parseLong(request.getParameter(PRODUCT_CATEGORY_ID)));
        product.setPrice(Integer.parseInt(request.getParameter(PRICE)));
        product.setIsPizza(isPizza);
        productDAO.insertProduct(product);
    }

    private void setAttributesIfProductIsPizza(HttpServletRequest request, HttpSession httpSession) {
        List<AdditionalIngredient> ingredientList = additionalIngredientDAO.getAllAdditionalIngredient();
        List<ProductSize> productSizeList = productSizeDAO.getAllProductSize();
        request.setAttribute(INGREDIENT_LIST, ingredientList);
        httpSession.setAttribute(PRODUCT_SIZE_LIST, productSizeList);
    }

    private void setBasicAttributes(HttpSession httpSession, HttpServletRequest request, Boolean isPizza) {
        Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
        List<ProductCategoryLocale> productCategoryLocaleList = productCategoryLocaleDAO.getAllProductCategoryLocale(localeId);
        request.setAttribute(MIN_PRODUCT_PRICE_PARAMETER, MIN_PRODUCT_PRICE);
        request.setAttribute(IS_PIZZA, isPizza);
        request.setAttribute(PRODUCT_CATEGORIES, productCategoryLocaleList);
    }
}
