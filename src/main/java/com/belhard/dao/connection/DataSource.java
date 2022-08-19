package com.belhard.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSource implements AutoCloseable {

    private Connection connection;
    private static Logger logger = LogManager.getLogger(DataSource.class);

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
            logger.info("connection to database completed");
        } catch (SQLException e) {
            logger.error("connection to database didn't complete");
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
