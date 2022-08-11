package main.java.com.belhard.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }

    private void init() {
        ConnectionProperties props = new ConnectionProperties();
        try {
            connection = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
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
