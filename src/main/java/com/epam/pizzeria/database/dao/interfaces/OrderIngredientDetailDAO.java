package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.OrderIngredientDetail;

import java.util.List;

public interface OrderIngredientDetailDAO {
    void insertOrderIngredientDetail(OrderIngredientDetail orderIngredientDetail);
    List<OrderIngredientDetail> getAllOrderIngredientDetailByOrderDetailId(Long orderDetailId);
}
