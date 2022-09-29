package com.belhard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.belhard.dao.BookDao;
import com.belhard.dao.OrderInfoDao;
import com.belhard.dao.entity.OrderInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Repository
public class OrderInfoDaoImpl implements OrderInfoDao {

	private static final String INSERT = "INSERT INTO order_infos (book_id, order_id, book_quantity, book_price) "
					+ "VALUES (:book_id, :order_id, :book_quantity, :book_price)";
	private static final String GET_BY_ID = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
					+ "FROM order_infos i WHERE i.order_infos_id = :infos_id AND i.deleted = false";
	private static final String GET_ALL = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
					+ "FROM order_infos i WHERE i.deleted = false";
	private static final String GET_ALL_PAGED = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
					+ "FROM order_infos i WHERE i.deleted = false ORDER BY i.order_infos_id LIMIT :limit OFFSET :offset";
	private static final String GET_BY_ORDER_ID = "SELECT i.order_infos_id, i.book_id, i.order_id, i.book_quantity, i.book_price "
					+ "FROM order_infos i WHERE i.order_id = :order_id AND i.deleted = false";
	private static final String UPDATE = "UPDATE order_infos SET book_id = :book_id, order_id = :order_id, "
					+ "book_quantity = :book_quantity, book_price = :book_price WHERE order_infos_id = :infos_id AND deleted = false";
	private static final String GET_COUNT_ALL_ORDER_INFOS = "SELECT count(o.order_infos_id) AS total FROM order_infos i "
					+ "WHERE i.deleted = false";
	private static final String DELETE = "UPDATE order_infos SET deleted = true WHERE order_infos_id = :infos_id";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final BookDao bookDao;

	@Override
	public OrderInfo create(OrderInfo entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("book_id", entity.getBook().getId());
		params.addValue("order_id", entity.getOrderId());
		params.addValue("book_quantity", entity.getBookQuantity());
		params.addValue("book_price", entity.getBookPrice());
		jdbcTemplate.update(INSERT, params, keyHolder);

		Map<String, Object> keys = keyHolder.getKeys();
		if (keys.get("order_infos_id") == null) {
			throw new RuntimeException("Couldn't create new user");
		}
		Long id = (Long) keys.get("order_infos_id");
		return get(id);
	}

	@Override
	public List<OrderInfo> getByOrderId(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("order_id", id);
		return jdbcTemplate.query(GET_BY_ORDER_ID, params, this::mapRow);
	}

	@Override
	public OrderInfo get(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("infos_id", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, params, this::mapRow);
	}

	private OrderInfo mapRow(ResultSet resultSet, int num) throws SQLException {
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
		return jdbcTemplate.query(GET_ALL, this::mapRow);
	}

	@Override
	public List<OrderInfo> getAll(int limit, long offset) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", limit);
		params.addValue("offset", offset);
		return jdbcTemplate.query(GET_ALL_PAGED, params, this::mapRow);
	}

	@Override
	public long countAll() {
		Long count = jdbcTemplate.query(GET_COUNT_ALL_ORDER_INFOS, (rs) -> {
			if (rs.next()) {
				return rs.getLong("total");
			}
			throw new RuntimeException("ERROR: count of details not definition");
		});
		return count;
	}

	@Override
	public OrderInfo update(OrderInfo entity) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("book_id", entity.getBook().getId());
		params.addValue("order_id", entity.getOrderId());
		params.addValue("book_quantity", entity.getBookQuantity());
		params.addValue("book_price", entity.getBookPrice());
		params.addValue("infos_id", entity.getId());
		int rowUpdate = jdbcTemplate.update(UPDATE, params);
		if (rowUpdate == 0) {
			throw new RuntimeException("Couldn't update details with id=" + entity.getId());
		}
		return get(entity.getId());
	}

	@Override
	public boolean delete(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("infos_id", id);
		return jdbcTemplate.update(DELETE, params) == 1;
	}
}