package com.belhard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.belhard.dao.BookDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.entity.OrderInfo;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderInfoDaoImpl implements OrderInfoDao {

	private static final String INSERT = "INSERT INTO order_infos (book_id, order_id, book_quantity, book_price) VALUES (?, ?, ?, ?)";
	private static final String GET_BY_ID = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
			+ "FROM order_infos i WHERE i.order_infos_id = ? AND i.deleted = false";
	private static final String GET_ALL = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
			+ "FROM order_infos i WHERE i.deleted = false";
	private static final String GET_ALL_PAGED = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
			+ "FROM order_infos i WHERE i.deleted = false ORDER BY i.order_infos_id LIMIT ? OFFSET ?";
	private static final String GET_BY_ORDER_ID = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
			+ "FROM order_infos i WHERE i.order_id = ? AND i.deleted = false";
	private static final String UPDATE = "UPDATE order_infos SET book_id = ?, order_id = ?, book_quantity = ?, book_price = ? "
			+ "WHERE order_infos_id = ? AND deleted = false";
	private static final String GET_COUNT_ALL_ORDER_INFOS = "SELECT count(o.order_infos_id) AS total FROM order_infos i "
			+ "WHERE i.deleted = false" ;
	private static final String DELETE = "UPDATE order_infos SET deleted = true WHERE order_infos_id = ?";

	private final DataSource dataSource;
	private final BookDao bookDao;

	public OrderInfoDaoImpl(DataSource dataSource, BookDao bookDao) {
		this.dataSource = dataSource;
		this.bookDao = bookDao;
	}

	@Override
	public OrderInfo create(OrderInfo entity) {
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, entity.getBook().getId());
			statement.setLong(2, entity.getOrderId());
			statement.setInt(3, entity.getBookQuantity());
			statement.setBigDecimal(4, entity.getBookPrice());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			log.debug("database access completed successfully");
			if (keys.next()) {
				Long id = keys.getLong("order_infos_id");
				return get(id);
			}
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return null;
	}

	@Override
	public List<OrderInfo> getByOrderId(Long id) {
		List<OrderInfo> details = new ArrayList<>();
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(GET_BY_ORDER_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				details.add(process(resultSet));
			}
			log.debug("database access completed successfully");
			return details;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return details;
	}

	@Override
	public OrderInfo get(Long id) {
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			log.debug("database access completed successfully");
			if (resultSet.next()) {
				return process(resultSet);
			}
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return null;
	}

	private OrderInfo process(ResultSet resultSet) throws SQLException {
		OrderInfo entity = new OrderInfo();
		entity.setId(resultSet.getLong("order_infos_id"));
		entity.setBook(bookDao.get(resultSet.getLong("book_id")));
		entity.setOrderId(resultSet.getLong("order_id"));
		entity.setBookQuantity(resultSet.getInt("book_quantity"));
		entity.setBookPrice(resultSet.getBigDecimal("book_price"));
		return entity;
	}

	@Override
	public List<OrderInfo> getAll() {
		List<OrderInfo> list = new ArrayList<>();
		try (Connection connection = dataSource.getFreeConnections(); Statement statement = connection.createStatement()) {
			ResultSet result = statement.executeQuery(GET_ALL);
			while (result.next()) {
				list.add(process(result));
			}
			log.debug("database access completed successfully");
			return list;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return list;
	}

	@Override
	public List<OrderInfo> getAll(int limit, long offset) {
		List<OrderInfo> list = new ArrayList<>();
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGED)) {
			statement.setInt(1, limit);
			statement.setLong(2, offset);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				list.add(process(result));
			}
			log.debug("database access completed successfully");
			return list;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return list;
	}

	@Override
	public long countAll() {
		try (Connection connection = dataSource.getFreeConnections(); Statement statement = connection.createStatement()) {
			ResultSet result = statement.executeQuery(GET_COUNT_ALL_ORDER_INFOS);
			if (result.next()) {
				log.debug("database access completed successfully");
				return result.getLong("total");
			}
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		throw new RuntimeException("ERROR: count of order_infos not definition");
	}

	@Override
	public OrderInfo update(OrderInfo entity) {
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setLong(1, entity.getBook().getId());
			statement.setLong(2, entity.getOrderId());
			statement.setLong(3, entity.getBookQuantity());
			statement.setBigDecimal(4, entity.getBookPrice());
			statement.setLong(5, entity.getId());
			statement.executeUpdate();
			log.debug("database access completed successfully");
			return get(entity.getId());
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return null;
	}
	
	@Override
	public boolean delete(Long id) {
		try (Connection connection = dataSource.getFreeConnections();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setLong(1, id);
			int rowsDelete = statement.executeUpdate();
			log.debug("database access completed successfully");
			return rowsDelete == 1;
		} catch (SQLException e) {
			log.error("database access completed unsuccessfully", e);
		}
		return false;
	}
}