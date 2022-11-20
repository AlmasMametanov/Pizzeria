package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.BasketDAO;
import com.epam.pizzeria.entity.Basket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BasketDAOImpl implements BasketDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_BASKET = "INSERT INTO basket (user_id, product_id, size_id) VALUES (?, ?, ?)";
    private static final String INSERT_BASKET_FOR_NOT_PIZZA = "INSERT INTO basket (user_id, product_id) VALUES (?, ?)";
    private static final String GET_PRODUCTS_IF_PRODUCT_IS_PIZZA = "SELECT * FROM basket WHERE user_id = ? AND product_id = ? AND size_id = ?";
    private static final String GET_PRODUCTS_IF_SIZE_IS_NULL = "SELECT * FROM basket WHERE user_id = ? AND product_id = ? AND size_id is null";
    private static final String GET_ALL_BASKETS_FROM_BY_USER_ID = "SELECT * FROM basket WHERE user_id = ?";
    private static final String INCREMENT_PRODUCT_COUNT_PER_UNIT_IN_BASKET_BY_BASKET_ID = "UPDATE basket SET count = count + 1 WHERE id = ?";
    private static final String CHANGE_PRODUCT_COUNT_IN_BASKET_BY_BASKET_ID = "UPDATE basket SET count = ? WHERE id = ?";
    private static final String DELETE_BASKET_BY_ID = "DELETE FROM basket WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertBasketForPizza(Basket basket) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BASKET, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, basket.getUserId());
            preparedStatement.setLong(2, basket.getProductId());
            preparedStatement.setLong(3, basket.getProductSizeId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                basket.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void insertBasketForNotPizza(Basket basket) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BASKET_FOR_NOT_PIZZA)) {
            preparedStatement.setLong(1, basket.getUserId());
            preparedStatement.setLong(2, basket.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Basket> getProductsIfProductIsPizza(Long userId, Long productId, Long productSizeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Basket> baskets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_IF_PRODUCT_IS_PIZZA)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, productSizeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToBasketList(baskets, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return baskets;
    }

    @Override
    public Basket getProductIfProductIsNotPizza(Long userId, Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Basket basket = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_IF_SIZE_IS_NULL)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                basket = new Basket();
                setParametersToBasket(basket, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return basket;
    }

    @Override
    public List<Basket> getAllBasketsByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Basket> baskets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BASKETS_FROM_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToBasketList(baskets, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return baskets;
    }

    @Override
    public void incrementProductCountPerUnitInBasket(Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INCREMENT_PRODUCT_COUNT_PER_UNIT_IN_BASKET_BY_BASKET_ID)) {
            preparedStatement.setLong(1, basketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateProductCountInBasket(Integer productCount, Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRODUCT_COUNT_IN_BASKET_BY_BASKET_ID)) {
            preparedStatement.setInt(1, productCount);
            preparedStatement.setLong(2, basketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteBasketById(Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BASKET_BY_ID)) {
            preparedStatement.setLong(1, basketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToBasket(Basket basket, ResultSet resultSet) throws SQLException {
        basket.setId(resultSet.getLong("id"));
        basket.setProductId(resultSet.getLong("product_id"));
        basket.setUserId(resultSet.getLong("user_id"));
        basket.setCount(resultSet.getInt("count"));
        basket.setProductSizeId(resultSet.getLong("size_id"));
    }

    private void setParametersToBasketList(List<Basket> baskets, ResultSet resultSet) throws SQLException {
        Basket basket = new Basket();
        setParametersToBasket(basket, resultSet);
        baskets.add(basket);
    }
}
