package com.javalab.movieapp.dao;

import com.javalab.movieapp.action.user.UpdateUserInfoAction;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.ResourceBundle.getBundle;

public class ConnectionPool {

    private static final String SERVER_TIME_ZONE = "GMT+0:00";
    private static final String DATABASE_RESOURCES = "database";
    private static final String DB_DRIVER_KEY = "db.driver";
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String DB_POOLSIZE_KEY = "db.poolsize";

    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;
    private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoAction.class);

    public static ConnectionPool getInstance() {
        if (instance == null) {
            ResourceBundle rb = getBundle(DATABASE_RESOURCES);
            String driver = rb.getString(DB_DRIVER_KEY);
            String url = rb.getString(DB_URL_KEY);
            String user = rb.getString(DB_USER_KEY);
            String password = rb.getString(DB_PASSWORD_KEY);
            String poolSizeStr = rb.getString(DB_POOLSIZE_KEY);
            int poolSize = Integer.parseInt(poolSizeStr);
            try {
                instance = new ConnectionPool(driver, url,
                        user, password, poolSize);
            } catch (ClassNotFoundException e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private ConnectionPool(String driver, String url,
                           String user, String password, int poolSize)
            throws ClassNotFoundException, SQLException {
        TimeZone timeZone = TimeZone.getTimeZone(SERVER_TIME_ZONE);
        TimeZone.setDefault(timeZone);
        Class.forName(driver);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url, user, password);
            connectionQueue.offer(connection);
        }
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                if (!connectionQueue.offer(connection)) {
                    LOGGER.info("Connection not added. Possible `leakage` of connections");
                }
            } else {
                LOGGER.info("Trying to release closed connection. Possible `leakage` of connections");
            }
        } catch (SQLException e) {
            LOGGER.error("Connection not added");
        }
    }
}
