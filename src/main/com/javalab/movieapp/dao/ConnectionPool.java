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

    public static final int DEFAULT_POOL_SIZE = 10;
    public static final String SERVER_TIME_ZONE = "GMT+0:00";
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;
    private static final Logger LOGGER = Logger.getLogger(UpdateUserInfoAction.class);

    public static ConnectionPool getInstance() {
        if (instance == null) {
            ResourceBundle rb = getBundle("database");
            String driver = rb.getString("db.driver");
            String url = rb.getString("db.url");
            String user = rb.getString("db.user");
            String password = rb.getString("db.password");
            String poolSizeStr = rb.getString("db.poolsize");
            int poolSize = (poolSizeStr != null) ?
                    Integer.parseInt(poolSizeStr) : DEFAULT_POOL_SIZE;
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
            LOGGER.error( "Connection not added");
        }
    }
}
