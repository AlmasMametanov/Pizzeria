package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductIngredientDetailDAO;
import com.epam.pizzeria.entity.ProductIngredientDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class ProductIngredientDetailDAOImpl implements ProductIngredientDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_INGREDIENT_DETAIL = "INSERT INTO product_ingredient_detail (product_id, ingredient_id) VALUES (?, ?)";
    private static final String GET_ALL_PRODUCT_INGREDIENT_DETAIL_BY_PRODUCT_ID = "SELECT ingredient_id FROM product_ingredient_detail WHERE product_id = ?";
    private static final String DELETE_PRODUCT_INGREDIENT_DETAIL = "DELETE FROM product_ingredient_detail WHERE ingredient_id = ? AND product_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertProductIngredientDetail(ProductIngredientDetail productIngredientDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_INGREDIENT_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, productIngredientDetail.getProductId());
            preparedStatement.setLong(2, productIngredientDetail.getIngredientId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productIngredientDetail.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Long> getAllProductIngredientDetailByProductId(Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Long> productIngredientDetailList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_INGREDIENT_DETAIL_BY_PRODUCT_ID)) {
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productIngredientDetailList.add(resultSet.getLong("ingredient_id"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productIngredientDetailList;
    }

    @Override
    public void deleteProductIngredientDetail(Long ingredientId, Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_INGREDIENT_DETAIL)) {
            preparedStatement.setLong(1, ingredientId);
            preparedStatement.setLong(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
