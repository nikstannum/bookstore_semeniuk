package com.belhard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.belhard.dao.OrderDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.Order.Status;
import com.belhard.dao.entity.OrderInfo;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderDaoImpl implements OrderDao {

	public static final String INSERT = "INSERT INTO orders (user_id, status_id, total_cost) "
			+ "VALUES (?, (SELECT s.status_id FROM status s WHERE s.name = ?), ?)";
	public static final String GET_BY_ID = "SELECT o.order_id, o.user_id, "
			+ "(SELECT s.name AS name FROM status s WHERE s.status_id = o.status_id), o.total_cost "
			+ "FROM orders o WHERE o.order_id = ? AND o.deleted = false";
	public static final String GET_ALL = "SELECT o.order_id, o.user_id, "
			+ "(SELECT s.name AS name FROM status s WHERE o.status_id = s.status_id), o.total_cost FROM orders o WHERE o.deleted = false";
	public static final String GET_ALL_PAGED = "SELECT o.order_id, o.user_id, (SELECT s.name AS name FROM status s "
			+ "WHERE o.status_id = s.status_id), o.total_cost FROM orders o WHERE o.deleted = false ORDER BY o.order_id LIMIT ? OFFSET ?";
	public static final String UPDATE = "UPDATE orders SET user_id = ?, status_id = (SELECT s.status_id FROM status s WHERE s.name = ?), "
			+ "total_cost = ? WHERE order_id = ? AND deleted = false";
	public static final String DELETE = "UPDATE users SET deleted = true WHERE order_id = ?";
	public static final String GET_COUNT_ALL_ORDERS = "SELECT count(o.order_id) AS all_orders FROM orders o WHERE o.deleted = false";

	private final DataSource dataSource;
	private final OrderInfoDao orderInfoDao;
	private final UserDao userDao;

	public OrderDaoImpl(DataSource dataSource, OrderInfoDao orderInfoDao, UserDao userDao) {
		this.dataSource = dataSource;
		this.orderInfoDao = orderInfoDao;
		this.userDao = userDao;
	}

	@Override
	public Order create(Order entity) {
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, entity.getUser().getId());
			statement.setString(2, Order.Status.PENDING.toString());
			statement.setBigDecimal(3, entity.getTotalCost());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			log.info("database access completed successfully");
			if (keys.next()) {
				Long id = keys.getLong("order_id");
				Order order = get(id);
				List<OrderInfo> infoDetails = entity.getDetails();
				List<OrderInfo> createdDetails = new ArrayList<>();
				for (OrderInfo elm : infoDetails) {
					elm.setOrderId(id);
					createdDetails.add(orderInfoDao.create(elm));
				}
				order.setDetails(createdDetails);
				return order;
			}
			connection.commit();
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return null;
	}

	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
	}

	private void close(Connection connection) {
		try {
			connection.setAutoCommit(true);
			connection.close();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Order get(Long id) {
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID, Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return processOrder(resultSet);
			}
			connection.commit();
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return null;
	}

	@Override
	public List<Order> getAll() {
		List<Order> list = new ArrayList<>();
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(GET_ALL);
			while (result.next()) {
				list.add(processOrder(result));
			}
			log.debug("database access completed successfully");
			connection.commit();
			return list;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return list;
	}

	@Override
	public List<Order> getAll(int limit, long offset) {
		List<Order> list = new ArrayList<>();
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGED);
			statement.setInt(1, limit);
			statement.setLong(2, offset);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				list.add(processOrder(result));
			}
			log.debug("database access completed successfully");
			connection.commit();
			return list;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return list;
	}

	@Override
	public long countAll() {
		try (Connection connection = dataSource.getFreeConnections(); Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(GET_COUNT_ALL_ORDERS);
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return resultSet.getLong("all_orders");
			}
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		throw new RuntimeException("ERROR: count of users not definition");
	}

	@Override
	public Order update(Order entity) {
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(UPDATE);
			statement.setLong(1, entity.getUser().getId());
			statement.setString(2, entity.getStatus().toString());
			statement.setBigDecimal(3, entity.getTotalCost());
			statement.setLong(4, entity.getId());
			statement.executeUpdate();
			List<OrderInfo> infos = entity.getDetails();
			for (OrderInfo elm : infos) {
				orderInfoDao.update(elm);
			}
			log.debug("database access completed successfully");
			connection.commit();
			return get(entity.getId());
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return null;
	}

	@Override
	public boolean removeRedundantDetails(List<Long> listId) {
		for (Long id : listId) {
			orderInfoDao.delete(id);
		}
		return true;
	}

	@Override
	public boolean delete(Long id) {
		Connection connection = dataSource.getFreeConnections();
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(DELETE);
			statement.setLong(1, id);
			List<OrderInfo> infos = orderInfoDao.getByOrderId(id);
			for (OrderInfo elm : infos) {
				orderInfoDao.delete(elm.getId());
			}
			int rowsDelete = statement.executeUpdate();
			log.debug("database access completed successfully");
			connection.commit();
			return rowsDelete == 1;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return false;
	}

	private Order processOrder(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong("order_id"));
		order.setUser(userDao.get(resultSet.getLong("user_id")));
		order.setStatus(Status.valueOf(resultSet.getString("name")));
		order.setTotalCost(resultSet.getBigDecimal("total_cost"));
		order.setDetails(orderInfoDao.getByOrderId(order.getId()));
		return order;
	}

}
