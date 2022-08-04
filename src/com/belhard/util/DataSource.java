package com.belhard.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource implements AutoCloseable {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/bookstore";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }

    private void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
