package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductIngredientDetailDAO;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDetailDAO;
import com.epam.pizzeria.entity.Product;
import com.epam.pizzeria.entity.ProductSizeDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT = "INSERT INTO product (name, description, price, is_pizza, " +
            "product_category_id, image_url) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String GET_ALL_PRODUCTS_BY_NAME = "SELECT * FROM product WHERE name = ?";
    private static final String GET_ALL_PRODUCT = "SELECT * FROM product ORDER BY product_category_id";
    private static final String GET_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM product WHERE product_category_id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, description = ?, price = ?, image_url = ?, " +
            "is_pizza = ?, product_category_id = ? WHERE id = ?";
    private static final String UPDATE_PRODUCT_ACTIVE_STATUS = "UPDATE product SET is_active = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertProduct(Product product) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setBoolean(4, product.getIsPizza());
            preparedStatement.setLong(5, product.getProductCategoryId());
            preparedStatement.setString(6, product.getImageUrl());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Product product = new Product();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProduct(product, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> getAllProductsByName(String productName) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_BY_NAME)) {
            preparedStatement.setString(1, productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductList(products, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> getAllProduct() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductList(productList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productList;
    }

    @Override
    public List<Product> getAllProductsByCategoryId(Long productCategoryId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_BY_CATEGORY_ID)) {
            preparedStatement.setLong(1, productCategoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductList(products, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return products;
    }

    @Override
    public void updateProduct(Product product) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setString(4, product.getImageUrl());
            preparedStatement.setBoolean(5, product.getIsPizza());
            preparedStatement.setLong(6, product.getProductCategoryId());
            preparedStatement.setLong(7, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateProductActiveStatus(Product product) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_ACTIVE_STATUS)) {
            preparedStatement.setBoolean(1, product.getIsActive());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteProductById(Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setLong(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToProduct(Product product, ResultSet resultSet) throws SQLException {
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getInt("price"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setProductCategoryId(resultSet.getLong("product_category_id"));
        product.setIsPizza(resultSet.getBoolean("is_pizza"));
        product.setIsActive(resultSet.getBoolean("is_active"));
        if (product.getIsPizza()) {
            ProductSizeDetailDAO productSizeDetailDAO = new ProductSizeDetailDAOImpl();
            product.setProductSizeDetailList(productSizeDetailDAO.getAllProductSizeDetailByProductId(product.getId()));
            ProductIngredientDetailDAO productIngredientDetailDAO = new ProductIngredientDetailDAOImpl();
            product.setProductIngredientDetailList(productIngredientDetailDAO.getAllProductIngredientDetailByProductId(product.getId()));
        }
    }

    private void setParametersToProductList(List<Product> productList, ResultSet resultSet) throws SQLException {
        Product product = new Product();
        setParametersToProduct(product, resultSet);
        productList.add(product);
    }
}
