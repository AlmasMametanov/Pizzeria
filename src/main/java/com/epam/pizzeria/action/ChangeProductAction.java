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
import java.util.stream.Collectors;

import static com.epam.pizzeria.action.ActionConstants.GET_ALL_PRODUCT_ACTION;
import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.USER;

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
            Long productId = Long.parseLong(request.getParameter("productId"));
            Product product = productDAO.getProductById(productId);
            product.setName(request.getParameter("productName"));
            if (product.getIsPizza()) {
                checkIngredientsForChanges(request, product);
                updateProductSizePrice(request);
            } else {
                changesIfProductIsNotPizza(request, product);
            }
            if (request.getParameter("imageUrl") != "")
                product.setImageUrl(request.getParameter("imageUrl"));
            productDAO.updateProduct(product);
            actionFactory.getAction(GET_ALL_PRODUCT_ACTION).execute(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void changesIfProductIsNotPizza(HttpServletRequest request, Product product) {
        String description = request.getParameter("description");
        Integer price = Integer.parseInt(request.getParameter("price"));
        product.setDescription(description);
        product.setPrice(price);
    }

    private void updateProductSizePrice(HttpServletRequest request) {
        List<String> productSizePriceList = Arrays.asList(request.getParameterValues("productSizeDetailPrice"));
        List<String> productSizeDetailIdList = Arrays.asList(request.getParameterValues("productSizeDetailId"));
        for (int i = 0; i < productSizeDetailIdList.size(); i++) {
            productSizeDetailDAO.updatePriceById(Long.parseLong(productSizeDetailIdList.get(i)), Integer.parseInt(productSizePriceList.get(i)));
        }
    }

    private void checkIngredientsForChanges(HttpServletRequest request, Product product) {
        if (request.getParameter("selectedIngredientId") != null) {
            List<String> oldProductIngredientIdList = new ArrayList<>(Arrays.asList(request.getParameterValues("productIngredientIdList")));
            List<String> newIngredientsIdForPizza = new ArrayList<>(Arrays.asList(request.getParameterValues("selectedIngredientId")));
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
