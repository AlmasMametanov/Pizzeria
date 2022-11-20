package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.BasketIngredientDetailDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import com.epam.pizzeria.entity.BasketIngredientDetail;
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

public class BasketIngredientDetailDAOImpl implements BasketIngredientDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_BASKET_INGREDIENT_DETAIL = "INSERT INTO basket_ingredient_detail (additional_ingredient_detail_id, basket_id) VALUES (?, ?)";
    private static final String GET_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID = "SELECT additional_ingredient_detail_id FROM basket_ingredient_detail WHERE basket_id = ?";
    private static final String GET_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID_AND_INGREDIENT_ID_AND_SIZE_ID = "SELECT bid.id, basket_id, bid.additional_ingredient_detail_id, aid.size_id, ai.name, aid.price FROM " +
            "basket_ingredient_detail bid join additional_ingredient_detail aid join additional_ingredient ai ON bid.additional_ingredient_detail_id = aid.id AND aid.additional_ingredient_id = ai.id WHERE bid.basket_id = ?";
    private static final String DELETE_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID = "DELETE FROM basket_ingredient_detail WHERE basket_id = ?";
    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertBasketIngredientDetail(BasketIngredientDetail basketIngredientDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BASKET_INGREDIENT_DETAIL)) {
            preparedStatement.setLong(1, basketIngredientDetail.getAdditionalIngredientDetailId());
            preparedStatement.setLong(2, basketIngredientDetail.getBasketId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
    @Override
    public List<Long> getIngredientIdListByBasketId(Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Long> ingredientIdOfBasketList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID)) {
            preparedStatement.setLong(1, basketId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredientIdOfBasketList.add(resultSet.getLong("additional_ingredient_detail_id"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return ingredientIdOfBasketList;
    }

    @Override
    public List<BasketIngredientDetail> getBasketIngredientDetailById(Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<BasketIngredientDetail> basketIngredientDetailList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID_AND_INGREDIENT_ID_AND_SIZE_ID)) {
            preparedStatement.setLong(1, basketId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToBasketIngredientDetailList(basketIngredientDetailList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return basketIngredientDetailList;
    }

    @Override
    public void deleteBasketIngredientDetailByBasketId(Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BASKET_INGREDIENT_DETAIL_BY_BASKET_ID)) {
            preparedStatement.setLong(1, basketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private AdditionalIngredient setParametersToAdditionalIngredient(ResultSet resultSet) throws SQLException {
        AdditionalIngredient additionalIngredient = new AdditionalIngredient();
        additionalIngredient.setName(resultSet.getString("name"));
        return additionalIngredient;
    }

    private ProductSizeIngredientDetail setParametersToProductSizeIngredientDetail(ResultSet resultSet) throws SQLException {
        ProductSizeIngredientDetail productSizeIngredientDetail = new ProductSizeIngredientDetail();
        productSizeIngredientDetail.setPrice(resultSet.getInt("price"));
        return productSizeIngredientDetail;
    }

    private void setParametersToBasketIngredientDetail(BasketIngredientDetail basketIngredientDetail, ResultSet resultSet) throws SQLException {
        basketIngredientDetail.setId(resultSet.getLong("id"));
        basketIngredientDetail.setAdditionalIngredientDetailId(resultSet.getLong("additional_ingredient_detail_id"));
        basketIngredientDetail.setBasketId(resultSet.getLong("basket_id"));
        basketIngredientDetail.setAdditionalIngredient(setParametersToAdditionalIngredient(resultSet));
        basketIngredientDetail.setProductSizeIngredientDetail(setParametersToProductSizeIngredientDetail(resultSet));
    }

    private void setParametersToBasketIngredientDetailList(List<BasketIngredientDetail> basketIngredientDetailList, ResultSet resultSet) throws SQLException {
        BasketIngredientDetail basketIngredientDetail = new BasketIngredientDetail();
        setParametersToBasketIngredientDetail(basketIngredientDetail, resultSet);
        basketIngredientDetailList.add(basketIngredientDetail);
    }
}
