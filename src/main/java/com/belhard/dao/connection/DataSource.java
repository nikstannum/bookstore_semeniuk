package com.belhard.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSource implements AutoCloseable {

    private ProxyConnection connection;
    private static final Logger logger = LogManager.getLogger(DataSource.class);

    public Connection getConnection() {
        if (connection == null) {
            init();
        }
        return connection;
    }

    private void init() {
        ConnectionProperties props = new ConnectionProperties();
        try {
            Connection realConnection = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
            this.connection = new ProxyConnection(realConnection);
            logger.info("connection to database completed");
        } catch (SQLException e) {
            logger.error("connection to database didn't complete");
        }
    }


    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.reallyClose();
                logger.info("connection really closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
