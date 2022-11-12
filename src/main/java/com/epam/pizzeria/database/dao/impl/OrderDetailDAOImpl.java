package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.OrderDetailDAO;
import com.epam.pizzeria.entity.OrderDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO order_detail (order_id, product_id, count, price, size_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ORDER_DETAIL_IF_SIZE_IS_NULL = "INSERT INTO order_detail (order_id, product_id, count, price) " +
            "VALUES (?, ?, ?, ?)";
    private static final String GET_All_ORDER_DETAIL_BY_ORDER_ID = "SELECT * FROM order_detail WHERE order_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertOrderDetail(OrderDetail orderDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, orderDetail.getOrderId());
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getCount());
            preparedStatement.setInt(4, orderDetail.getPrice());
            preparedStatement.setLong(5, orderDetail.getProductSizeId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderDetail.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void insertOrderDetailIfSizeIsNull(OrderDetail orderDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_DETAIL_IF_SIZE_IS_NULL)) {
            preparedStatement.setLong(1, orderDetail.getOrderId());
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getCount());
            preparedStatement.setInt(4, orderDetail.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    @Override
    public List<OrderDetail> getAllOrderDetailByOrderId(Long orderId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_All_ORDER_DETAIL_BY_ORDER_ID)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrderDetailList(orderDetails, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderDetails;
    }

    private void setParametersToOrderDetail(OrderDetail orderDetail, ResultSet resultSet) throws SQLException {
        orderDetail.setId(resultSet.getLong("id"));
        orderDetail.setOrderId(resultSet.getLong("order_id"));
        orderDetail.setProductId(resultSet.getLong("product_id"));
        orderDetail.setCount(resultSet.getInt("count"));
        orderDetail.setPrice(resultSet.getInt("price"));
        orderDetail.setProductSizeId(resultSet.getLong("size_id"));
    }

    private void setParametersToOrderDetailList(List<OrderDetail> orderDetails, ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        setParametersToOrderDetail(orderDetail, resultSet);
        orderDetails.add(orderDetail);
    }
}
