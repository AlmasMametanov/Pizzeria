package com.epam.pizzeria.database.dao.impl;

import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.AdditionalIngredientDAO;
import com.epam.pizzeria.entity.AdditionalIngredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;

public class AdditionalIngredientDAOImpl implements AdditionalIngredientDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ADDITIONAL_INGREDIENT = "INSERT INTO additional_ingredient (name, image_url) VALUES (?, ?)";
    private static final String GET_ADDITIONAL_INGREDIENT_NAME_BY_ID = "SELECT name FROM additional_ingredient WHERE id = ?";
    private static final String GET_ADDITIONAL_INGREDIENT_BY_ID = "SELECT * FROM additional_ingredient WHERE id = ?";
    private static final String GET_ALL_ACTIVE_ADDITIONAL_INGREDIENT = "SELECT * FROM additional_ingredient WHERE is_active = 1";
    private static final String GET_ALL_ADDITIONAL_INGREDIENT = "SELECT * FROM additional_ingredient";
    private static final String UPDATE_ADDITIONAL_INGREDIENT = "UPDATE additional_ingredient SET name = ?, image_url = ? WHERE id = ?";
    private static final String UPDATE_ADDITIONAL_INGREDIENT_ACTIVE_STATUS = "UPDATE additional_ingredient SET is_active = ? WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertAdditionalIngredient(AdditionalIngredient additionalIngredient) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDITIONAL_INGREDIENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, additionalIngredient.getName());
            preparedStatement.setString(2, additionalIngredient.getImageUrl());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                additionalIngredient.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public String getAdditionalIngredientNameById(Long additionalIngredientId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        String additionalIngredientName = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDITIONAL_INGREDIENT_NAME_BY_ID)) {
            preparedStatement.setLong(1, additionalIngredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                additionalIngredientName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return additionalIngredientName;
    }

    @Override
    public AdditionalIngredient getAdditionalIngredientById(Long additionalIngredientId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        AdditionalIngredient additionalIngredient = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDITIONAL_INGREDIENT_BY_ID)) {
            preparedStatement.setLong(1, additionalIngredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                additionalIngredient = new AdditionalIngredient();
                setParametersToAdditionalIngredient(additionalIngredient, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return additionalIngredient;
    }

    @Override
    public List<AdditionalIngredient> getAllActiveAdditionalIngredient() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<AdditionalIngredient> additionalIngredientList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ACTIVE_ADDITIONAL_INGREDIENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAdditionalIngredientList(additionalIngredientList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return additionalIngredientList;
    }

    @Override
    public List<AdditionalIngredient> getAllAdditionalIngredient() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<AdditionalIngredient> additionalIngredientList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ADDITIONAL_INGREDIENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToAdditionalIngredientList(additionalIngredientList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return additionalIngredientList;
    }

    @Override
    public void updateAdditionalIngredient(AdditionalIngredient additionalIngredient) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDITIONAL_INGREDIENT)) {
            preparedStatement.setString(1, additionalIngredient.getName());
            preparedStatement.setString(2, additionalIngredient.getImageUrl());
            preparedStatement.setLong(3, additionalIngredient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateAdditionalIngredientActiveStatus(AdditionalIngredient additionalIngredient) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDITIONAL_INGREDIENT_ACTIVE_STATUS)) {
            preparedStatement.setBoolean(1, additionalIngredient.getIsActive());
            preparedStatement.setLong(2, additionalIngredient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToAdditionalIngredient(AdditionalIngredient additionalIngredient, ResultSet resultSet) throws SQLException {
        additionalIngredient.setId(resultSet.getLong("id"));
        additionalIngredient.setName(resultSet.getString("name"));
        additionalIngredient.setImageUrl(resultSet.getString("image_url"));
        additionalIngredient.setIsActive(resultSet.getBoolean("is_active"));
    }

    private void setParametersToAdditionalIngredientList(List<AdditionalIngredient> additionalIngredientList, ResultSet resultSet) throws SQLException {
        AdditionalIngredient additionalIngredient = new AdditionalIngredient();
        setParametersToAdditionalIngredient(additionalIngredient, resultSet);
        additionalIngredientList.add(additionalIngredient);
    }
}
