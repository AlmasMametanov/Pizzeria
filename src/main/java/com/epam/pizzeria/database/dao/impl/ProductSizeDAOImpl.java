package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDAO;
import com.epam.pizzeria.entity.ProductSize;
import com.epam.pizzeria.entity.ProductSizeDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class ProductSizeDAOImpl implements ProductSizeDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_MIN_PIZZA_SIZE_PRICE = "SELECT price FROM size ORDER BY price ASC limit 1";
    private static final String GET_PRODUCT_SIZE_BY_SIZE_ID_AND_PRODUCT_ID = "SELECT s.id, name, size, price FROM size s JOIN product_size_detail psd ON s.id = psd.size_id WHERE s.id = ? AND product_id = ?";
    private static final String GET_PRODUCT_SIZE_BY_SIZE_ID = "SELECT * FROM size WHERE id = ?";
    private static final String GET_PIZZA_SIZE_PRICE_BY_ID = "SELECT price FROM size WHERE id = ?";
    private static final String GET_ALL_PRODUCT_SIZE = "SELECT * FROM size";

    ConnectionPool connectionPool;
    Connection connection;


    @Override
    public Integer getMinPizzaSizePrice() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Integer minPrice = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MIN_PIZZA_SIZE_PRICE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                minPrice = resultSet.getInt("price");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return minPrice;
    }

    @Override
    public ProductSize getProductSizeById(Long productSizeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        ProductSize productSize = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_SIZE_BY_SIZE_ID)) {
            preparedStatement.setLong(1, productSizeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productSize = new ProductSize();
                setParametersToProductSize(productSize, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSize;
    }

    @Override
    public ProductSize getProductSizeBySizeIdAndProductId(Long productSizeId, Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        ProductSize productSize = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_SIZE_BY_SIZE_ID_AND_PRODUCT_ID)) {
            preparedStatement.setLong(1, productSizeId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productSize = new ProductSize();
                setParametersToProductSizeDetail(productSize, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSize;
    }

    @Override
    public Integer getPizzaSizePriceById(Long pizzaSizeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Integer pizzaPrice = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PIZZA_SIZE_PRICE_BY_ID)) {
            preparedStatement.setLong(1, pizzaSizeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pizzaPrice = resultSet.getInt("price");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return pizzaPrice;
    }

    @Override
    public List<ProductSize> getAllProductSize() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<ProductSize> productSizeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SIZE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductSizeList(productSizeList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSizeList;
    }

    @Override
    public void removePizzaSize(ProductSize productSize) {


    }

    private void setParametersToProductSizeDetail(ProductSize productSize, ResultSet resultSet) throws SQLException {
        setParametersToProductSize(productSize, resultSet);
        ProductSizeDetail productSizeDetail = new ProductSizeDetail();
        productSizeDetail.setPrice(resultSet.getInt("price"));
        productSize.setProductSizeDetail(productSizeDetail);
    }

    private void setParametersToProductSize(ProductSize productSize, ResultSet resultSet) throws SQLException {
        productSize.setId(resultSet.getLong("id"));
        productSize.setName(resultSet.getString("name"));
        productSize.setSize(resultSet.getString("size"));
    }

    private void setParametersToProductSizeList(List<ProductSize> productSizeList, ResultSet resultSet) throws SQLException {
        ProductSize productSize = new ProductSize();
        setParametersToProductSize(productSize, resultSet);
        productSizeList.add(productSize);
    }
}
