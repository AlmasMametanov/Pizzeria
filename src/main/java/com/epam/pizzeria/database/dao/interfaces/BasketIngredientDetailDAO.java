package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.BasketIngredientDetail;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;

import java.util.List;

public interface BasketIngredientDetailDAO {
    void insertBasketIngredientDetail(BasketIngredientDetail basketIngredientDetail);
    Boolean getBasketIngredientDetailByBasketIdAndIngredientId(Long basketId, Long ingredientId);
    List<Long> getIngredientIdListByBasketId(Long basketId);
    List<BasketIngredientDetail> getBasketIngredientDetailById(Long basketId);
    void deleteBasketIngredientDetailByBasketId(Long basketId);

}
