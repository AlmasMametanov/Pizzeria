package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.LocaleDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductCategoryDAOImpl;
import com.epam.pizzeria.database.dao.impl.ProductCategoryLocaleDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.LocaleDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryLocaleDAO;
import com.epam.pizzeria.entity.Locale;
import com.epam.pizzeria.entity.ProductCategory;
import com.epam.pizzeria.entity.ProductCategoryLocale;
import com.epam.pizzeria.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.epam.pizzeria.action.ActionConstants.*;
import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class CreateCategoryAction implements Action {
    private ProductCategoryDAO productCategoryDAO = new ProductCategoryDAOImpl();
    private ProductCategoryLocaleDAO productCategoryLocaleDAO = new ProductCategoryLocaleDAOImpl();
    private ProductCategory productCategory = new ProductCategory();
    private LocaleDAO localeDAO = new LocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        User checkIfUserIsAdmin = (User) httpSession.getAttribute(USER);
        if (checkIfUserIsAdmin != null && checkIfUserIsAdmin.getIsAdmin()) {
            String categoryName = request.getParameter(CATEGORY_NAME);
            if (categoryName != null) {
                List<Locale> locales = (List<Locale>) httpSession.getAttribute(LOCALES);
                List<String> categoryLocaleNames = Arrays.asList(request.getParameterValues(CATEGORY_NAME));
                productCategoryDAO.insertProductCategory(productCategory);
                setValuesIntoCategoryLocaleAndInsertIntoDatabase(categoryLocaleNames, locales);
                request.getRequestDispatcher(ADMIN_PANEL_JSP).forward(request, response);
            } else {
                List<Locale> locales = localeDAO.getAllLocale();
                httpSession.setAttribute(LOCALES, locales);
                request.getRequestDispatcher(CREATE_NEW_CATEGORY_JSP).forward(request, response);
            }
        } else {
            response.sendRedirect(PAGE_NOT_FOUND_ACTION);
        }
    }

    private void setValuesIntoCategoryLocaleAndInsertIntoDatabase(List<String> productCategoryLocaleNames, List<Locale> locales) {
        ProductCategoryLocale productCategoryLocale;
        for (int i = 0; i < productCategoryLocaleNames.size(); i++) {
            productCategoryLocale = new ProductCategoryLocale();
            productCategoryLocale.setProductCategoryId(productCategory.getId());
            productCategoryLocale.setLocaleId(locales.get(i).getId());
            productCategoryLocale.setName(productCategoryLocaleNames.get(i));
            productCategoryLocaleDAO.insertProductCategoryLocale(productCategoryLocale);
        }
    }
}
