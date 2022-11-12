package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.DeliveryMethodLocaleDAO;
import com.epam.pizzeria.entity.DeliveryMethodLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class DeliveryMethodLocaleDAOImpl implements DeliveryMethodLocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_DELIVERY_METHOD_LOCALE_BY_ID = "SELECT * FROM delivery_method_locale WHERE id = ?";
    private static final String GET_DELIVERY_METHOD_LOCALE_BY_LOCALE_ID = "SELECT * FROM delivery_method_locale WHERE locale_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public DeliveryMethodLocale getDeliveryMethodLocaleById(Long id) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        DeliveryMethodLocale deliveryMethodLocale = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DELIVERY_METHOD_LOCALE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                deliveryMethodLocale = new DeliveryMethodLocale();
                setParametersToDeliveryMethodLocale(deliveryMethodLocale, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return deliveryMethodLocale;
    }

    @Override
    public List<DeliveryMethodLocale> getAllDeliveryMethodLocaleByLocaleId(Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<DeliveryMethodLocale> deliveryMethodLocaleList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DELIVERY_METHOD_LOCALE_BY_LOCALE_ID)) {
            preparedStatement.setLong(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToDeliveryMethodLocaleList(deliveryMethodLocaleList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return deliveryMethodLocaleList;
    }

    private void setParametersToDeliveryMethodLocale(DeliveryMethodLocale deliveryMethodLocale, ResultSet resultSet) throws SQLException {
        deliveryMethodLocale.setId(resultSet.getLong("id"));
        deliveryMethodLocale.setLocaleId(resultSet.getLong("locale_id"));
        deliveryMethodLocale.setDeliveryMethodId(resultSet.getLong("delivery_method_id"));
        deliveryMethodLocale.setName(resultSet.getString("name"));
    }

    private void setParametersToDeliveryMethodLocaleList(List<DeliveryMethodLocale> deliveryMethodLocaleList, ResultSet resultSet) throws SQLException {
        DeliveryMethodLocale deliveryMethodLocale = new DeliveryMethodLocale();
        setParametersToDeliveryMethodLocale(deliveryMethodLocale, resultSet);
        deliveryMethodLocaleList.add(deliveryMethodLocale);
    }
}
