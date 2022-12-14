package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.ProductSizeDetail;

import java.util.List;

public interface ProductSizeDetailDAO {

    void insertPizzaSizeDetail(ProductSizeDetail productSizeDetail);
    List<ProductSizeDetail> getAllProductSizeDetailByProductId(Long productId);
    void updatePriceById(Long id, Integer price);
}
