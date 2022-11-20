package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.BasketIngredientDetail;

import java.util.List;

public interface BasketIngredientDetailDAO {
    void insertBasketIngredientDetail(BasketIngredientDetail basketIngredientDetail);
    List<Long> getIngredientIdListByBasketId(Long basketId);
    List<BasketIngredientDetail> getBasketIngredientDetailById(Long basketId);
    void deleteBasketIngredientDetailByBasketId(Long basketId);

}
