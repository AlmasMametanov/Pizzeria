package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.OrderIngredientDetailDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.OrderDetail;
import com.epam.pizzeria.entity.OrderIngredientDetail;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class OrderIngredientDetailDAOImpl implements OrderIngredientDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ORDER_INGREDIENT_DETAIL = "INSERT INTO order_ingredient_detail (order_detail_id, additional_ingredient_detail_id) values (?, ?)";
    private static final String GET_All_ORDER_INGREDIENT_DETAIL_BY_ORDER_DETAIL_ID = "SELECT * FROM order_ingredient_detail oid " +
            "JOIN additional_ingredient_detail aid JOIN additional_ingredient ai ON oid.additional_ingredient_detail_id = aid.id " +
            "AND aid.additional_ingredient_id = ai.id WHERE order_detail_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertOrderIngredientDetail(OrderIngredientDetail orderIngredientDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_INGREDIENT_DETAIL)) {
            preparedStatement.setLong(1, orderIngredientDetail.getOrderDetailId());
            preparedStatement.setLong(2, orderIngredientDetail.getAdditionalIngredientDetailId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<OrderIngredientDetail> getAllOrderIngredientDetailByOrderDetailId(Long orderDetailId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<OrderIngredientDetail> orderIngredientDetails = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_All_ORDER_INGREDIENT_DETAIL_BY_ORDER_DETAIL_ID)) {
            preparedStatement.setLong(1, orderDetailId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrderIngredientDetailList(orderIngredientDetails, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderIngredientDetails;
    }

    private AdditionalIngredient setParametersToAdditionalIngredient(ResultSet resultSet) throws SQLException {
        AdditionalIngredient additionalIngredient = new AdditionalIngredient();
        additionalIngredient.setName(resultSet.getString("name"));
        return additionalIngredient;
    }

    private ProductSizeIngredientDetail setParametersToProductSizeIngredientDetail(ResultSet resultSet) throws SQLException {
        ProductSizeIngredientDetail productSizeIngredientDetail = new ProductSizeIngredientDetail();
        productSizeIngredientDetail.setPrice(resultSet.getInt("price"));
        productSizeIngredientDetail.setAdditionalIngredient(setParametersToAdditionalIngredient(resultSet));
        return productSizeIngredientDetail;
    }

    private void setParametersToOrderIngredientDetail(OrderIngredientDetail orderIngredientDetail, ResultSet resultSet) throws SQLException {
        orderIngredientDetail.setId(resultSet.getLong("id"));
        orderIngredientDetail.setOrderDetailId(resultSet.getLong("order_detail_id"));
        orderIngredientDetail.setAdditionalIngredientDetailId(resultSet.getLong("additional_ingredient_detail_id"));
        orderIngredientDetail.setProductSizeIngredientDetail(setParametersToProductSizeIngredientDetail(resultSet));
    }

    private void setParametersToOrderIngredientDetailList(List<OrderIngredientDetail> orderIngredientDetails, ResultSet resultSet) throws SQLException {
        OrderIngredientDetail orderIngredientDetail = new OrderIngredientDetail();
        setParametersToOrderIngredientDetail(orderIngredientDetail, resultSet);
        orderIngredientDetails.add(orderIngredientDetail);
    }
}
