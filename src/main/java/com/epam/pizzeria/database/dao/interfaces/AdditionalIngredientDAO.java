package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.AdditionalIngredient;

import java.util.List;

public interface AdditionalIngredientDAO {
    void insertAdditionalIngredient(AdditionalIngredient additionalIngredient);
    String getAdditionalIngredientNameById(Long additionalIngredientId);
    AdditionalIngredient getAdditionalIngredientById(Long additionalIngredientId);
    List<AdditionalIngredient> getAllActiveAdditionalIngredient();
    List<AdditionalIngredient> getAllAdditionalIngredient();
    void updateAdditionalIngredient(AdditionalIngredient additionalIngredient);
    void updateAdditionalIngredientActiveStatus(AdditionalIngredient additionalIngredient);
}
