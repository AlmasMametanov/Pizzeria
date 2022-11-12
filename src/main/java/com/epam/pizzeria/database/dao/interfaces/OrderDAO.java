package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.Order;

import java.util.List;

public interface OrderDAO {
    void insertOrder(Order order);
    Long getLastOrderIdByUserId(Long userId);
    List<Order> getAllOrdersByUserIdAndLocaleId(Long userId, Long localeId);
    void updateOrderStatusByOrderId(Long statusId, Long orderId);
}
