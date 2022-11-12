package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.Locale;

import java.util.List;

public interface LocaleDAO {
    Long getLocaleIdByShortName(String localeShortName);
    List<Locale> getAllLocale();
}
