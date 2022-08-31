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
	public final int poolSize = Integer.parseInt(ConfigurationManager.INSTANCE.getProperty("db.pool_size"));
	private final Logger log = LogManager.getLogger(DataSource.class);

	DataSource() {
		freeConnections = new LinkedBlockingDeque<>(poolSize);
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
		ConfigurationManager props = ConfigurationManager.INSTANCE;
		try {
			Class.forName(props.getProperty("db.driver"));
			Connection realConnection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"),
					props.getProperty("db.password"));
			for (int i = 0; i < poolSize; i++) {
				freeConnections.add(new ProxyConnection(realConnection));
			}
			log.info("connection to database completed");
		} catch (SQLException e) {
			log.error("connection to database didn't complete");
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
	}

	public void releaseConnection(ProxyConnection connection) {
		givenAwayConnections.remove(connection);
		freeConnections.offer(connection);
	}

	private void destroyPoll() {
		try {
			for (int i = 0; i < poolSize; i++) {
				freeConnections.take().reallyClose();
			}
			log.info("Poll successfully destroyed");
		} catch (InterruptedException e) {
			log.error("Poll not destroyed");
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		destroyPoll();
	}
}
