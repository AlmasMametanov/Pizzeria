package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.ProductSizeDetailDAO;
import com.epam.pizzeria.entity.ProductSize;
import com.epam.pizzeria.entity.ProductSizeDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class ProductSizeDetailDAOImpl implements ProductSizeDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT_SIZE_DETAIL = "INSERT INTO product_size_detail (product_id, size_id, price) " +
            "VALUES (?, ?, ?)";
    private static final String GET_PRODUCT_SIZE_DETAIL_BY_ID = "SELECT * FROM product_size_detail WHERE id";
    private static final String GET_ALL_PRODUCT_SIZE_DETAIL_BY_PRODUCT_ID = "SELECT psd.id, size_id, product_id, price, name, size " +
            "FROM product_size_detail psd JOIN size s ON psd.size_id = s.id WHERE product_id = ?";
    private static final String UPDATE_PRICE_BY_ID = "UPDATE product_size_detail SET price = ? WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertPizzaSizeDetail(ProductSizeDetail productSizeDetail) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SIZE_DETAIL)) {
            preparedStatement.setLong(1, productSizeDetail.getProductId());
            preparedStatement.setLong(2, productSizeDetail.getProductSizeId());
            preparedStatement.setInt(3, productSizeDetail.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public ProductSizeDetail getProductSizeDetailById(Long id) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        ProductSizeDetail productSizeDetail = new ProductSizeDetail();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_SIZE_DETAIL_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductSizeDetail(productSizeDetail, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSizeDetail;
    }

    public List<ProductSizeDetail> getAllProductSizeDetailByProductId(Long productId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<ProductSizeDetail> productSizeDetailList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCT_SIZE_DETAIL_BY_PRODUCT_ID)) {
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToProductSizeDetailList(productSizeDetailList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productSizeDetailList;
    }

    @Override
    public void updatePriceById(Long id, Integer price) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRICE_BY_ID)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private ProductSize setParametersToProductSize(ResultSet resultSet) throws SQLException {
        ProductSize productSize = new ProductSize();
        productSize.setName(resultSet.getString("name"));
        productSize.setSize(resultSet.getString("size"));
        return productSize;
    }

    private void setParametersToProductSizeDetail(ProductSizeDetail productSizeDetail, ResultSet resultSet) throws SQLException {
        productSizeDetail.setId(resultSet.getLong("id"));
        productSizeDetail.setProductSizeId(resultSet.getLong("size_id"));
        productSizeDetail.setProductId(resultSet.getLong("product_id"));
        productSizeDetail.setPrice(resultSet.getInt("price"));
        productSizeDetail.setProductSize(setParametersToProductSize(resultSet));
    }

    private void setParametersToProductSizeDetailList(List<ProductSizeDetail> productSizeDetailList, ResultSet resultSet) throws SQLException {
        ProductSizeDetail productSizeDetail = new ProductSizeDetail();
        setParametersToProductSizeDetail(productSizeDetail, resultSet);
        productSizeDetailList.add(productSizeDetail);
    }
}
