package com.belhard.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.belhard.dao.OrderDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.UserDao;
import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.Order.Status;
import com.belhard.dao.entity.OrderInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

	public static final String INSERT = "INSERT INTO orders (user_id, status_id, total_cost) "
					+ "VALUES (:user_id, (SELECT s.status_id FROM status s WHERE s.name = :status_name), :total_cost)";
	public static final String GET_BY_ID = "SELECT o.order_id, o.user_id, "
					+ "(SELECT s.name AS name FROM status s WHERE s.status_id = o.status_id), o.total_cost "
					+ "FROM orders o WHERE o.order_id = :order_id AND o.deleted = false";
	public static final String GET_ALL = "SELECT o.order_id, o.user_id, "
					+ "(SELECT s.name AS name FROM status s WHERE o.status_id = s.status_id), o.total_cost "
					+ "FROM orders o WHERE o.deleted = false";
	public static final String GET_ALL_PAGED = "SELECT o.order_id, o.user_id, (SELECT s.name AS name FROM status s "
					+ "WHERE o.status_id = s.status_id), o.total_cost FROM orders o WHERE o.deleted = false "
					+ "ORDER BY o.order_id LIMIT :limit OFFSET :offset";
	public static final String UPDATE = "UPDATE orders SET user_id = :user_id, status_id = (SELECT s.status_id FROM status s "
					+ "WHERE s.name = :status_name), total_cost = :total_cost WHERE order_id = :order_id AND deleted = false";
	public static final String DELETE = "UPDATE users SET deleted = true WHERE order_id = :order_id";
	public static final String GET_COUNT_ALL_ORDERS = "SELECT count(o.order_id) AS all_orders FROM orders o WHERE o.deleted = false";

	private final OrderInfoDao orderInfoDao;
	private final UserDao userDao;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Order create(Order entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user_id", entity.getUser().getId());
		params.addValue("status_name", Order.Status.PENDING.toString());
		params.addValue("total_cost", entity.getTotalCost());
		Connection connection = null;
		try {
			connection = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
			connection.setAutoCommit(false);
			jdbcTemplate.update(INSERT, params, keyHolder);
			Map<String, Object> keys = keyHolder.getKeys();
			if (keys.get("order_id") == null) {
				throw new RuntimeException("Couldn't create new user");
			}
			Long id = (Long) keys.get("order_id");
			Order order = get(id);
			List<OrderInfo> infoDetails = entity.getDetails();
			List<OrderInfo> createdDetails = new ArrayList<>();
			for (OrderInfo elm : infoDetails) {
				elm.setOrderId(id);
				createdDetails.add(orderInfoDao.create(elm));
			}
			order.setDetails(createdDetails);
			connection.commit();
			return order;
		} catch (Exception e) {
			log.error(e.getMessage());
			rollback(connection);
		} finally {
			close(connection);
		}
		return null;
	}

	private void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			log.error(e.getMessage());
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
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("order_id", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, params, this::mapRow);
	}

	@Override
	public List<Order> getAll() {
		return jdbcTemplate.query(GET_ALL, this::mapRow);
	}

	@Override
	public List<Order> getAll(int limit, long offset) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", limit);
		params.addValue("offset", offset);
		return jdbcTemplate.query(GET_ALL_PAGED, params, this::mapRow);
	}

	@Override
	public long countAll() {
		Long count = jdbcTemplate.query(GET_COUNT_ALL_ORDERS, (rs) -> {
			if (rs.next()) {
				return rs.getLong("all_orders");
			}
			throw new RuntimeException("ERROR: count of details not definition");
		});
		return count;
	}

	@Override
	public Order update(Order entity) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user_id", entity.getUser().getId());
		params.addValue("status_name", entity.getStatus().toString());
		params.addValue("total_cost", entity.getTotalCost());
		params.addValue("order_id", entity.getId());
		Connection connection = null;
		try {
			connection = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
			connection.setAutoCommit(false);
			jdbcTemplate.update(UPDATE, params);
			List<OrderInfo> infos = entity.getDetails();
			for (OrderInfo elm : infos) {
				orderInfoDao.update(elm);
			}
			connection.commit();
			return get(entity.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
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
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("order_id", id);
		Connection connection = null;
		try {
			connection = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection();
			connection.setAutoCommit(false);
			List<OrderInfo> infos = get(id).getDetails();
			for (OrderInfo elm : infos) {
				orderInfoDao.delete(elm.getId());
			}
			int rowDelete = jdbcTemplate.update(DELETE, params);
			connection.commit();
			return rowDelete == 1;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
			rollback(connection);
		} finally {
			close(connection);
		}
		return false;
	}

	private Order mapRow(ResultSet resultSet, int num) throws SQLException {
		Order order = new Order();
		order.setId(resultSet.getLong("order_id"));
		order.setUser(userDao.get(resultSet.getLong("user_id")));
		order.setStatus(Status.valueOf(resultSet.getString("name")));
		order.setTotalCost(resultSet.getBigDecimal("total_cost"));
		order.setDetails(orderInfoDao.getByOrderId(order.getId()));
		return order;
	}

}
