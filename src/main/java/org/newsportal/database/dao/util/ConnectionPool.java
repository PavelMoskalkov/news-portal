package org.newsportal.database.dao.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static String DRIVER;
    private static int MAX_CONNECTIONS;
    private final BlockingQueue<Connection> pool;
    private static ConnectionPool connectionPool;

    private static void loadProperties() throws IOException {
        FileReader fileReader = new FileReader("/Users/pavel/Documents/Lessons/news-portal/src/main/resources/db.properties" );
        Properties properties = new Properties();
        properties.load(fileReader);
        URL = properties.getProperty("url" );
        USERNAME = properties.getProperty("username" );
        PASSWORD = properties.getProperty("password");
        DRIVER = properties.getProperty("driver");
        MAX_CONNECTIONS = Integer.parseInt(properties.getProperty("max_connections"));
    }

    public static ConnectionPool getConnectionPool() {
        synchronized (Object.class) {
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
            return connectionPool;
        }
    }

    private ConnectionPool() {
        try {
            loadProperties();
            pool = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
            Class.forName(DRIVER);
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                pool.put(createConnection());
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        try {
            pool.put(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
