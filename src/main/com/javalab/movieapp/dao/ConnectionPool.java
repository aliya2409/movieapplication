package com.javalab.movieapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.ResourceBundle.getBundle;

public class ConnectionPool {
    public static final int DEFAULT_POOL_SIZE = 10;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;


    public static void dispose() throws SQLException {
        if (instance != null) {
            instance.clearConnectionQueue();
            instance = null;
        }
    }

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
                //"Driver " + driver + " not found"
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
        Class.forName(driver);
        connectionQueue
                = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection
                    = DriverManager.getConnection(url, user, password);
            connectionQueue.offer(connection);
        }
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            //"Free connection waiting interrupted.
// Returned `null` connection", e
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                if (!connectionQueue.offer(connection)) {
                    System.out.println("Connection not added. Possible `leakage` of connections");
                }
            } else {
                System.out.println("Trying to release closed connection. Possible `leakage` of connections");
            }
        } catch (SQLException e) {
            System.out.println("SQLException at connection isClosed () checking.\n" +
                    "// Connection not added");
        }
    }

    private void clearConnectionQueue() throws SQLException {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            /* see java.sql.Connection#close () javadoc */
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }
}
