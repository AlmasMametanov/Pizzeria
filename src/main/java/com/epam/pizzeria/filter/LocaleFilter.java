package com.epam.pizzeria.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.pizzeria.util.constants.ParameterNamesConstants.*;

public class LocaleFilter implements Filter {
    private Long defaultLocaleID;
    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocaleID = Long.parseLong(filterConfig.getInitParameter("defaultLocaleID"));
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpServletRequest.getSession(true);

        Long localeId = (Long) httpSession.getAttribute(LOCALE_ID);
        String locale = (String) httpSession.getAttribute(LOCALE);

        if (localeId == null || locale == null) {
            httpSession.setAttribute(LOCALE_ID, defaultLocaleID);
            httpSession.setAttribute(LOCALE, defaultLocale);
        } else if (localeId.equals(2) && locale.equalsIgnoreCase("EN")) {
            httpSession.setAttribute(LOCALE_ID, 2);
            httpSession.setAttribute(LOCALE, "EN");
        } else if (localeId.equals(1) && locale.equalsIgnoreCase("RU")) {
            httpSession.setAttribute(LOCALE_ID, 1);
            httpSession.setAttribute(LOCALE, "RU");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
