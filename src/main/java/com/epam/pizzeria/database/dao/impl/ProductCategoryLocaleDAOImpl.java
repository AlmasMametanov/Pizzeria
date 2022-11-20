package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductCategoryLocaleDAO;
import com.epam.pizzeria.entity.ProductCategoryLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryLocaleDAOImpl implements ProductCategoryLocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_CATEGORY_LOCALE = "INSERT INTO product_category_locale (product_category_id, locale_id, name) " +
            "VALUES (?, ?, ?)";
    private static final String GET_ALL_PRODUCT_CATEGORY_BY_LOCALE_ID = "SELECT * FROM product_category_locale WHERE locale_id = ?";
    private static final String REMOVE_PRODUCT_CATEGORY_LOCALE = "DELETE FROM product_category_locale WHERE product_category_id = ?";

    ConnectionPool connectionPool;
    Connection connection;


    @Override
    public void insertProductCategoryLocale(ProductCategoryLocale productCategoryLocale) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_CATEGORY_LOCALE)) {
            preparedStatement.setLong(1, productCategoryLocale.getProductCategoryId());
            preparedStatement.setLong(2, productCategoryLocale.getLocaleId());
            preparedStatement.setString(3, productCategoryLocale.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<ProductCategoryLocale> getAllProductCategoryLocale(Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<ProductCategoryLocale> productCategoryLocaleList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_CATEGORY_BY_LOCALE_ID)) {
            preparedStatement.setLong(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductCategoryLocaleList(productCategoryLocaleList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productCategoryLocaleList;
    }

    @Override
    public void removeProductCategoryLocale(Long productCategoryId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCT_CATEGORY_LOCALE)) {
            preparedStatement.setLong(1, productCategoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToProductCategoryLocale(ProductCategoryLocale productCategoryLocale, ResultSet resultSet) throws SQLException {
        productCategoryLocale.setProductCategoryId(resultSet.getLong("product_category_id"));
        productCategoryLocale.setLocaleId(resultSet.getLong("locale_id"));
        productCategoryLocale.setName(resultSet.getString("name"));
    }

    private void setParametersToProductCategoryLocaleList(List<ProductCategoryLocale> productCategoryList, ResultSet resultSet) throws SQLException {
        ProductCategoryLocale productCategoryLocale = new ProductCategoryLocale();
        setParametersToProductCategoryLocale(productCategoryLocale, resultSet);
        productCategoryList.add(productCategoryLocale);
    }
}
