package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryDAO;
import com.epam.pizzeria.entity.ProductCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class ProductCategoryDAOImpl implements ProductCategoryDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_CATEGORY = "INSERT INTO product_category VALUES (null)";
    private static final String REMOVE_PRODUCT_CATEGORY = "DELETE FROM product_category WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertProductCategory(ProductCategory productCategory) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_CATEGORY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productCategory.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void removeProductCategory(Long productCategoryId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCT_CATEGORY)) {
            preparedStatement.setLong(1, productCategoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
