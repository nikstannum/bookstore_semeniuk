package com.belhard.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum DataSource implements AutoCloseable {
    INSTANCE;

    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;
    public static final int DEFAULT_POOL_SIZE = 2;
    private final Logger log = LogManager.getLogger(DataSource.class); // TODO why Logger must be nonstatic?

    DataSource() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        init();
    }


    public ProxyConnection getFreeConnections() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
            log.info("connection successfully issued");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("connection not issued");
        }
        return connection;
    }

    private void init() {
        ConnectionProperties props = new ConnectionProperties();
        try {
            Connection realConnection = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                freeConnections.add(new ProxyConnection(realConnection));
            }
            log.info("connection to database completed");
        } catch (SQLException e) {
            log.error("connection to database didn't complete");
        }
    }

    public void releaseConnection(ProxyConnection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPoll() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
                log.info("Poll successfully destroyed");
            } catch (InterruptedException e) {
                log.error("Poll not destroyed");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        destroyPoll();
    }
}
