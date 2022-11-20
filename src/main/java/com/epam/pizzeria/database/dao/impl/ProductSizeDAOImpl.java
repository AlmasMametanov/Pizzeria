package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDAO;
import com.epam.pizzeria.entity.ProductSize;
import com.epam.pizzeria.entity.ProductSizeDetail;
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

public class ProductSizeDAOImpl implements ProductSizeDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_PRODUCT_SIZE_BY_SIZE_ID_AND_PRODUCT_ID = "SELECT s.id, name, size, price FROM size s JOIN product_size_detail psd ON s.id = psd.size_id WHERE s.id = ? AND product_id = ?";
    private static final String GET_ALL_PRODUCT_SIZE = "SELECT * FROM size";
    private static final String GET_ALL_PRODUCT_SIZE_BY_INGREDIENT_ID = "SELECT * FROM size s JOIN additional_ingredient_detail aid " +
            "ON aid.size_id = s.id WHERE aid.additional_ingredient_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

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
                setParametersToProductSize(productSize, resultSet);
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
    public List<ProductSize> getAllProductSizeByIngredientId(Long ingredientId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<ProductSize> productSizeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SIZE_BY_INGREDIENT_ID)) {
            preparedStatement.setLong(1, ingredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProductSize productSize = new ProductSize();
                setParametersToProductSize(productSize, resultSet);
                setParametersToSizeIngredientDetail(productSize, resultSet);
                productSizeList.add(productSize);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSizeList;
    }

    private void setParametersToSizeIngredientDetail(ProductSize productSize, ResultSet resultSet) throws SQLException {
        ProductSizeIngredientDetail sizeIngredient = new ProductSizeIngredientDetail();
        sizeIngredient.setId(resultSet.getLong("aid.id"));
        sizeIngredient.setSizeId(resultSet.getLong("aid.size_id"));
        sizeIngredient.setIngredientId(resultSet.getLong("aid.additional_ingredient_id"));
        sizeIngredient.setPrice(resultSet.getInt("aid.price"));
        productSize.setProductSizeIngredientDetail(sizeIngredient);
    }

    private void setParametersToProductSizeDetail(ProductSize productSize, ResultSet resultSet) throws SQLException {
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
