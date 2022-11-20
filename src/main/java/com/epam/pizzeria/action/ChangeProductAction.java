package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.AdditionalIngredientDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductSizeDetailDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductIngredientDetailDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDetailDAO;
import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.ProductIngredientDetail;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeProductAction implements Action {
    private AdditionalIngredientDAO additionalIngredientDAO = new AdditionalIngredientDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private ProductSizeDetailDAO productSizeDetailDAO = new ProductSizeDetailDAOImpl();
    private ProductIngredientDetailDAO productIngredientDetailDAO = new ProductIngredientDetailDAOImpl();
    private ActionFactory actionFactory = ActionFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            Long productId = Long.parseLong(request.getParameter(PRODUCT_ID));
            Product product = productDAO.getProductById(productId);
            product.setName(request.getParameter(PRODUCT_NAME));
            if (product.getIsPizza()) {
                checkIngredientsForChanges(request, product);
                updateProductSizePrice(request);
            } else {
                changesIfProductIsNotPizza(request, product);
            }
            if (request.getParameter(IMAGE_URL) != "")
                product.setImageUrl(request.getParameter(IMAGE_URL));
            productDAO.updateProduct(product);
            actionFactory.getAction(GET_ALL_PRODUCT_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void changesIfProductIsNotPizza(HttpServletRequest request, Product product) {
        String description = request.getParameter(PRODUCT_DESCRIPTION);
        Integer price = Integer.parseInt(request.getParameter(PRICE));
        product.setDescription(description);
        product.setPrice(price);
    }

    private void updateProductSizePrice(HttpServletRequest request) {
        List<String> productSizePriceList = Arrays.asList(request.getParameterValues(PRODUCT_SIZE_DETAIL_PRICE));
        List<String> productSizeDetailIdList = Arrays.asList(request.getParameterValues(PRODUCT_SIZE_DETAIL_ID));
        for (int i = 0; i < productSizeDetailIdList.size(); i++) {
            productSizeDetailDAO.updatePriceById(Long.parseLong(productSizeDetailIdList.get(i)), Integer.parseInt(productSizePriceList.get(i)));
        }
    }

    private void checkIngredientsForChanges(HttpServletRequest request, Product product) {
        if (request.getParameter(SELECTED_INGREDIENT_ID) != null) {
            List<String> oldProductIngredientIdList = new ArrayList<>(Arrays.asList(request.getParameterValues(PRODUCT_INGREDIENT_ID_LIST)));
            List<String> newIngredientsIdForPizza = new ArrayList<>(Arrays.asList(request.getParameterValues(SELECTED_INGREDIENT_ID)));
            Collections.sort(oldProductIngredientIdList);
            Collections.sort(newIngredientsIdForPizza);
            if (!oldProductIngredientIdList.equals(newIngredientsIdForPizza)) {
                String description = concatenateIngredientsToCreatePizzaDescription(newIngredientsIdForPizza);
                product.setDescription(description);
                updateProductIngredientDetailListForProduct(oldProductIngredientIdList, newIngredientsIdForPizza, product);
            }
        }
    }

    private void updateProductIngredientDetailListForProduct(List<String> oldProductIngredientIdList, List<String> newIngredientsIdForPizza, Product product) {
        ProductIngredientDetail productIngredientDetail = null;
        for (int i = 0; i < newIngredientsIdForPizza.size(); i++) {
            if (!oldProductIngredientIdList.contains(newIngredientsIdForPizza.get(i))) {
                productIngredientDetail = new ProductIngredientDetail();
                productIngredientDetail.setProductId(product.getId());
                productIngredientDetail.setIngredientId(Long.parseLong(newIngredientsIdForPizza.get(i)));
                productIngredientDetailDAO.insertProductIngredientDetail(productIngredientDetail);
            } else {
                oldProductIngredientIdList.remove(newIngredientsIdForPizza.get(i));
            }
        }
        deleteOldProductIngredientDetailFromDatabase(oldProductIngredientIdList, product.getId());
    }

    private void deleteOldProductIngredientDetailFromDatabase(List<String> oldProductIngredientIdList, Long productId) {
        for (String oldIngredientId : oldProductIngredientIdList) {
            productIngredientDetailDAO.deleteProductIngredientDetail(Long.parseLong(oldIngredientId), productId);
        }
    }

    private String concatenateIngredientsToCreatePizzaDescription(List<String> newIngredientsIdForPizza) {
        List<String> ingredientNames = new ArrayList<>();
        for (String ingredientId : newIngredientsIdForPizza) {
            ingredientNames.add(additionalIngredientDAO.getAdditionalIngredientNameById(Long.parseLong(ingredientId)));
        }
        return String.join(", ", ingredientNames);
    }
}
