package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.DeliveryMethodLocale;

import java.util.List;

public interface DeliveryMethodLocaleDAO {
    DeliveryMethodLocale getDeliveryMethodLocaleById(Long id);
    List<DeliveryMethodLocale> getAllDeliveryMethodLocaleByLocaleId(Long localeId);
}
