package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.LocaleDAO;
import com.epam.pizzeria.entity.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocaleDAOImpl implements LocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_LOCALE_ID_BY_SHORT_NAME = "SELECT id FROM locale WHERE short_name = ?";
    private static final String GET_ALL_LOCALE = "SELECT * FROM locale";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public Long getLocaleIdByShortName(String localeShortName) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Long locale = 0L;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCALE_ID_BY_SHORT_NAME)) {
            preparedStatement.setString(1, localeShortName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locale = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return locale;
    }

    @Override
    public List<Locale> getAllLocale() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Locale> locales = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LOCALE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToLocaleList(locales, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return locales;
    }

    private void setParametersToLocale(Locale locale, ResultSet resultSet) throws SQLException {
        locale.setId(resultSet.getLong("id"));
        locale.setShortName(resultSet.getString("short_name"));
        locale.setName(resultSet.getString("name"));
    }

    private void setParametersToLocaleList(List<Locale> localeList, ResultSet resultSet) throws SQLException {
        Locale locale = new Locale();
        setParametersToLocale(locale, resultSet);
        localeList.add(locale);
    }
}
