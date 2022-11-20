package com.epam.pizzeria.database.dao.interfaces;

import com.epam.pizzeria.entity.Product;

import java.util.List;

public interface ProductDAO {
    void insertProduct(Product product);
    Product getProductById(Long productId);
    List<Product> getAllProduct();
    List<Product> getAllActiveProductsByCategoryId(Long productCategoryId);
    void updateProduct(Product product);
    void updateProductActiveStatus(Product product);
}
