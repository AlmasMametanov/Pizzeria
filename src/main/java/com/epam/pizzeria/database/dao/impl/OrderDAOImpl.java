package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.OrderDAO;
import com.epam.pizzeria.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ORDER = "INSERT INTO orders (user_id, status_id, total_price, date_start, ready_in, delivery_method_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_LAST_ORDER_ID_BY_USER_ID = "SELECT id FROM orders WHERE user_id = ? ORDER BY id DESC LIMIT 1";
    private static final String GET_ALL_ORDERS_BY_USER_ID_AND_LOCALE_ID = "SELECT * FROM orders o JOIN status_locale sl ON o.status_id = sl.status_id WHERE user_id = ? AND sl.locale_id = ? ORDER BY o.id DESC";
    private static final String UPDATE_ORDER_STATUS_BY_ORDER_ID = "UPDATE orders SET status_id = ? WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertOrder(Order order) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getStatusId());
            preparedStatement.setInt(3, order.getTotalPrice());
            preparedStatement.setTimestamp(4, order.getDateStart());
            preparedStatement.setString(5, order.getReadyIn());
            preparedStatement.setLong(6, order.getDeliveryMethodId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Long getLastOrderIdByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Long orderId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ORDER_ID_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderId;
    }

    @Override
    public List<Order> getAllOrdersByUserIdAndLocaleId(Long userId, Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_ID_AND_LOCALE_ID)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrderList(orders, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders;
    }

    @Override
    public void updateOrderStatusByOrderId(Long statusId, Long orderId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ORDER_ID)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setStatusId(resultSet.getLong("status_id"));
        order.setTotalPrice(resultSet.getInt("total_price"));
        order.setDateStart(resultSet.getTimestamp("date_start"));
        order.setReadyIn(resultSet.getString("ready_in"));
    }

    private void setParametersToOrderList(List<Order> orders, ResultSet resultSet) throws SQLException {
        Order order = new Order();
        setParametersToOrder(order, resultSet);
        orders.add(order);
    }
}
