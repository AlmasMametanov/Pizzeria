package com.epam.pizzeria.action;

import com.epam.pizzeria.database.dao.impl.LocaleDAOImpl;
import com.epam.pizzeria.database.dao.interfaces.LocaleDAO;
import com.epam.pizzeria.entity.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.epam.pizzeria.util.constants.PageNameConstants.*;
import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class ChangeLocaleAction implements Action {
    private LocaleDAO localeDAO = new LocaleDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);

        String selectedLanguage = request.getParameter(SELECTED_LOCALE);
        Long localeId = localeDAO.getLocaleIdByShortName(selectedLanguage);
        httpSession.setAttribute(LOCALE, selectedLanguage);
        httpSession.setAttribute(LOCALE_ID, localeId);
        response.sendRedirect(INDEX_JSP);
    }
}
