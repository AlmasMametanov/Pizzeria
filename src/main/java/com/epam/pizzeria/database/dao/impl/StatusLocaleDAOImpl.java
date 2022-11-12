package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.StatusLocaleDAO;
import com.epam.pizzeria.entity.Status;
import com.epam.pizzeria.entity.StatusLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusLocaleDAOImpl implements StatusLocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_STATUS_BY_STATUS_ID_AND_LOCALE_ID = "SELECT * FROM status_locale WHERE status_id = ? AND locale_id = ?";
    private static final String GET_ALL_STATUS_BY_LOCALE_ID = "SELECT * FROM status_locale WHERE locale_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public StatusLocale getStatusLocaleByStatusIdAndLocaleId(Long statusId, Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        StatusLocale status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_BY_STATUS_ID_AND_LOCALE_ID)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status = new StatusLocale();
                setParametersToStatusLocale(status, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    @Override
    public List<StatusLocale> getAllStatusByLocaleId(Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<StatusLocale> statusList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STATUS_BY_LOCALE_ID)) {
            preparedStatement.setLong(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToStatusLocaleList(statusList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statusList;
    }

    private void setParametersToStatusLocale(StatusLocale status, ResultSet resultSet) throws SQLException {
        status.setId(resultSet.getLong("id"));
        status.setLocaleId(resultSet.getLong("locale_id"));
        status.setStatusId(resultSet.getLong("status_id"));
        status.setName(resultSet.getString("name"));
    }

    private void setParametersToStatusLocaleList(List<StatusLocale> statusList, ResultSet resultSet) throws SQLException {
        StatusLocale status = new StatusLocale();
        setParametersToStatusLocale(status, resultSet);
        statusList.add(status);
    }
}
