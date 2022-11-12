package com.epam.pizzeria.database.connection;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Integer.parseInt;

public class ConnectionPool {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private String url;
    private String username;
    private String password;
    private String driverName;
    private ResourceBundle properties = ResourceBundle.getBundle("ConnectionPool");
    private final int POOL_SIZE = parseInt(properties.getString("db.poolSize"));
    private static volatile ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        setDataForConnection();
        loadDrivers();
        addConnection();
    }

    private void setDataForConnection() {
        Dotenv dotenv = Dotenv.configure().load();

        this.driverName = properties.getString("db.driver");
        this.url = properties.getString("db.url");
        this.username = properties.getString("db.username");
        this.password = dotenv.get("db.password");
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localCon = instance;
        if (localCon == null) {
            synchronized (ConnectionPool.class) {
                localCon = instance;
                if (localCon == null) {
                    instance = localCon = new ConnectionPool();
                }
            }
        }
        return localCon;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverName).newInstance();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void addConnection() {
        Connection connection;
        while (connectionQueue.size() < POOL_SIZE) {
            try {
                connection = DriverManager.getConnection(url, username, password);
                connectionQueue.put(connection);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        if ((connection != null) && (connectionQueue.size() < POOL_SIZE)) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
