package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.StatusLocale;

import java.util.List;

public interface StatusLocaleDAO {
    StatusLocale getStatusLocaleByStatusIdAndLocaleId(Long statusId, Long localeId);
    List<StatusLocale> getAllStatusByLocaleId(Long localeId);
}
