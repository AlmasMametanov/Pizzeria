package com.epam.pizzeria.database.dao.impl;

import static com.epam.pizzeria.database.connection.ConnectionPool.getInstance;
import com.epam.pizzeria.database.connection.ConnectionPool;
import com.epam.pizzeria.database.dao.interfaces.UserDAO;
import com.epam.pizzeria.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_USER = "INSERT INTO user (first_name, email, birthday, phone_number, " +
            "address, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM user";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String GET_USER_BY_EMAIL_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String UPDATE_USER_ADDRESS = "UPDATE user SET address = ? WHERE id = ?";
    private static final String UPDATE_USER_PHONE_NUMBER = "UPDATE user SET phone_number = ? WHERE id = ?";
    private static final String UPDATE_USER_BAN_STATUS = "UPDATE user SET is_banned = ? WHERE id = ?";

    private ConnectionPool connectionPool;
    private Connection connection;


    @Override
    public void insertUser(User user) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            java.sql.Date sqlBirthday = new java.sql.Date((user.getBirthday()).getTime());
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setDate(3,  sqlBirthday);
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> getAllUsers() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToUserList(users, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                setParametersToUser(user, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public User getUserByEmailPassword(String email, String password) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                setParametersToUser(user, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public void updateUserAddress(User user) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ADDRESS)) {
            preparedStatement.setString(1, user.getAddress());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateUserPhoneNumber(User user) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PHONE_NUMBER)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateUserBanStatus(User user) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BAN_STATUS)) {
            preparedStatement.setBoolean(1, user.getIsBanned());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setAddress(resultSet.getString("address"));
        user.setIsAdmin((resultSet.getBoolean("is_admin")));
        user.setIsBanned((resultSet.getBoolean("is_banned")));
    }

    private void setParametersToUserList(List<User> users, ResultSet resultSet) throws SQLException {
        User user = new User();
        setParametersToUser(user, resultSet);
        users.add(user);
    }
}
