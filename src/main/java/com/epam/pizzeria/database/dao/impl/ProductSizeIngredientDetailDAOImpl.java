package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeIngredientDetailDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.ProductSizeIngredientDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class ProductSizeIngredientDetailDAOImpl implements ProductSizeIngredientDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_INTO_PRODUCT_SIZE_INGREDIENT_DETAIL = "INSERT INTO additional_ingredient_detail (size_id, additional_ingredient_id, price) VALUES (?, ?, ?)";
    private static final String GET_ALL_PRODUCT_SIZE_INGREDIENT_DETAIL_BY_SIZE_ID = "SELECT aid.id, size_id, additional_ingredient_id, " +
            "price, name FROM additional_ingredient_detail aid JOIN additional_ingredient ai ON additional_ingredient_id = ai.id WHERE size_id = ?";
    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertProductSizeIngredientDetail(ProductSizeIngredientDetail productSizeIngredientDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_PRODUCT_SIZE_INGREDIENT_DETAIL)) {
            preparedStatement.setLong(1, productSizeIngredientDetail.getSizeId());
            preparedStatement.setLong(2, productSizeIngredientDetail.getIngredientId());
            preparedStatement.setInt(3, productSizeIngredientDetail.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<ProductSizeIngredientDetail> getAllProductSizeIngredientDetailBySizeId(Long sizeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<ProductSizeIngredientDetail> sizeIngredientList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SIZE_INGREDIENT_DETAIL_BY_SIZE_ID)) {
            preparedStatement.setLong(1, sizeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToSizeIngredientDetailList(sizeIngredientList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return sizeIngredientList;
    }

    private AdditionalIngredient setParametersToIngredient(ResultSet resultSet) throws SQLException {
        AdditionalIngredient additionalIngredient = new AdditionalIngredient();
        additionalIngredient.setName(resultSet.getString("name"));
        return additionalIngredient;
    }

    private void setParametersToSizeIngredientDetail(ProductSizeIngredientDetail sizeIngredient, ResultSet resultSet) throws SQLException {
        sizeIngredient.setId(resultSet.getLong("id"));
        sizeIngredient.setSizeId(resultSet.getLong("size_id"));
        sizeIngredient.setIngredientId(resultSet.getLong("additional_ingredient_id"));
        sizeIngredient.setPrice(resultSet.getInt("price"));
        sizeIngredient.setAdditionalIngredient(setParametersToIngredient(resultSet));
    }

    private void setParametersToSizeIngredientDetailList(List<ProductSizeIngredientDetail> sizeIngredientList, ResultSet resultSet) throws SQLException {
        ProductSizeIngredientDetail sizeIngredient = new ProductSizeIngredientDetail();
        setParametersToSizeIngredientDetail(sizeIngredient, resultSet);
        sizeIngredientList.add(sizeIngredient);
    }
}
