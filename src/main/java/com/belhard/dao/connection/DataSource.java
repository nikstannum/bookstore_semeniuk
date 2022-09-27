package com.belhard.dao.connection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class DataSource implements AutoCloseable {

	private BlockingQueue<ProxyConnection> freeConnections;
	private Queue<ProxyConnection> givenAwayConnections;
	public final int poolSize;
	private final ConfigurationManager configurationManager;

	@Autowired
	public DataSource(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
		poolSize = Integer.parseInt(configurationManager.getProperty("db.pool_size"));
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
		try {
			Class.forName(configurationManager.getProperty("db.driver"));
			Connection realConnection = DriverManager.getConnection(configurationManager.getProperty("db.url"),
							configurationManager.getProperty("db.user"),
							configurationManager.getProperty("db.password"));
			for (int i = 0; i < poolSize; i++) {
				freeConnections.add(new ProxyConnection(realConnection));
			}
			log.info("connection to database completed");
		} catch (SQLException e) {
			log.error("connection to database didn't complete", e);
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
		DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
			try {
				DriverManager.deregisterDriver(driver);
				log.info("Driver {} deregistered", driver);
			} catch (SQLException e) {
				log.error("Driver wasn't deregister", e);
			}
		});
	}

	public class ProxyConnection implements Connection {
		private final Connection realConnection;

		ProxyConnection(Connection realConnection) {
			this.realConnection = realConnection;
		}

		void reallyClose() {
			try {
				realConnection.close();
			} catch (SQLException e) {
			}
		}

		@Override
		public void close() throws SQLException {
			releaseConnection(this);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return realConnection.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return realConnection.isWrapperFor(iface);
		}

		@Override
		public Statement createStatement() throws SQLException {
			return realConnection.createStatement();
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return realConnection.prepareStatement(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return realConnection.prepareCall(sql);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return realConnection.nativeSQL(sql);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			realConnection.setAutoCommit(autoCommit);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return realConnection.getAutoCommit();
		}

		@Override
		public void commit() throws SQLException {
			realConnection.commit();
		}

		@Override
		public void rollback() throws SQLException {
			realConnection.rollback();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return realConnection.isClosed();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return realConnection.getMetaData();
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			realConnection.setReadOnly(readOnly);
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return realConnection.isReadOnly();
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			realConnection.setCatalog(catalog);
		}

		@Override
		public String getCatalog() throws SQLException {
			return realConnection.getCatalog();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			realConnection.setTransactionIsolation(level);
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return realConnection.getTransactionIsolation();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return realConnection.getWarnings();
		}

		@Override
		public void clearWarnings() throws SQLException {
			realConnection.clearWarnings();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return realConnection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
						throws SQLException {
			return realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
						throws SQLException {
			return realConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return realConnection.getTypeMap();
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			realConnection.setTypeMap(map);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			realConnection.setHoldability(holdability);
		}

		@Override
		public int getHoldability() throws SQLException {
			return realConnection.getHoldability();
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return realConnection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return realConnection.setSavepoint(name);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			realConnection.rollback(savepoint);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			realConnection.releaseSavepoint(savepoint);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
						throws SQLException {
			return realConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
						int resultSetHoldability) throws SQLException {
			return realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
						int resultSetHoldability) throws SQLException {
			return realConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return realConnection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return realConnection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return realConnection.prepareStatement(sql, columnNames);
		}

		@Override
		public Clob createClob() throws SQLException {
			return realConnection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return realConnection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return realConnection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return realConnection.createSQLXML();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return realConnection.isValid(timeout);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			realConnection.setClientInfo(name, value);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			realConnection.setClientInfo(properties);
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return realConnection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return realConnection.getClientInfo();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return realConnection.createArrayOf(typeName, elements);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return realConnection.createStruct(typeName, attributes);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			realConnection.setSchema(schema);
		}

		@Override
		public String getSchema() throws SQLException {
			return realConnection.getSchema();
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			realConnection.abort(executor);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			realConnection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return realConnection.getNetworkTimeout();
		}
	}

}
