package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.ProductCategoryDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductCategoryLocaleDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryLocaleDAO;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.action.ActionConstants.PAGE_NOT_FOUND_ACTION;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class DeleteCategoryAction implements Action {
    private ProductCategoryLocaleDAO productCategoryLocaleDAO = new ProductCategoryLocaleDAOImpl();
    private ProductCategoryDAO productCategoryDAO = new ProductCategoryDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User user = (User) httpSession.getAttribute(USER);
        if (user != null && user.getIsAdmin()) {
            Long productCategoryId = Long.valueOf(request.getParameter(CATEGORY_ID));
            productCategoryLocaleDAO.removeProductCategoryLocale(productCategoryId);
            productCategoryDAO.removeProductCategory(productCategoryId);
            response.sendRedirect(INDEX_JSP);
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }
}
