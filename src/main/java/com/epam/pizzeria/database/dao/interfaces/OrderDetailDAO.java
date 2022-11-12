package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO {
    void insertOrderDetail(OrderDetail orderDetail);
    void insertOrderDetailIfSizeIsNull(OrderDetail orderDetail);
    List<OrderDetail> getAllOrderDetailByOrderId(Long orderId);
}
