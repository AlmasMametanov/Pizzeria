package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.AdditionalIngredient;

import java.sql.Array;
import java.util.List;

public interface AdditionalIngredientDAO {
    void insertAdditionalIngredient(AdditionalIngredient additionalIngredient);
    String getAdditionalIngredientNameById(Long additionalIngredientId);
    List<AdditionalIngredient> getAllAdditionalIngredient();
}
