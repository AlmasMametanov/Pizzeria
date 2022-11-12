package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.ProductSizeIngredientDetailDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeIngredientDetailDAO;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.USER;

public class SelectProductSizeAction implements Action {
    ProductSizeIngredientDetailDAO productSizeIngredientDetailDAO = new ProductSizeIngredientDetailDAOImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null) {
            Long productId = Long.parseLong(request.getParameter("productId"));
            Long productSizeId = Long.parseLong(request.getParameter("productSizeId"));
            List<ProductSizeIngredientDetail> productSizeIngredientDetailList = productSizeIngredientDetailDAO.getAllProductSizeIngredientDetailBySizeId(productSizeId);
            request.setAttribute("productId", productId);
            request.setAttribute("productSizeId", productSizeId);
            request.setAttribute("productSizeIngredientDetailList", productSizeIngredientDetailList);
            request.getRequestDispatcher("chooseAdditionalIngredients.jsp").forward(request, response);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
