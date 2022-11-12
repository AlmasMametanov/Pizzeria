package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.Basket;

import java.util.List;

public interface BasketDAO {
    void insertBasketForPizza(Basket basket);
    void insertBasketForNotPizza(Basket basket);
    Long getLastBasketIdByUserId(Long userId);
    List<Basket> getProductsIfProductIsPizza(Long userId, Long productId, Long productSizeId);
    Basket getProductIfProductIsNotPizza(Long userId, Long productId);
    List<Basket> getAllBasketsByUserId(Long userId);
    void incrementProductCountPerUnitInBasket(Long basketId);
    void updateProductCountInBasket(Integer productCount, Long basketId);
    void deleteBasketByUserId(Long productId, Long userId);
    void deleteBasketById(Long basketId);
    void deleteAllBasketsByUserId(Long userId);
}
