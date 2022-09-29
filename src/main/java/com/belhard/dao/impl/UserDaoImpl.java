package com.belhard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.belhard.dao.UserDao;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

	private static final String GET_ALL_PAGED = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role "
					+ "FROM users u JOIN role r ON u.role_id = r.role_id WHERE u.deleted = false "
					+ "ORDER BY u.user_id LIMIT :limit OFFSET :offset";

	public static final String INSERT = "INSERT INTO users (first_name, last_name, email, password, role_id) "
					+ "VALUES (:firstName, :lastName, :email, :password, (SELECT r.role_id FROM role r WHERE r.name = :roleName))";

	public static final String GET_BY_ID = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role FROM users u "
					+ "JOIN role r ON u.role_id = r.role_id WHERE u.user_id = :id AND u.deleted = false";
	public static final String GET_ALL = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role FROM users u "
					+ "JOIN role r ON u.role_id = r.role_id WHERE u.deleted = false";
	public static final String GET_BY_EMAIL = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role FROM users u "
					+ "JOIN role r ON u.role_id = r.role_id WHERE u.email = :email AND u.deleted = false";
	public static final String GET_BY_LASTNAME = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role FROM users u "
					+ "JOIN role r ON u.role_id = r.role_id WHERE u.last_name = :lastName AND u.deleted = false";
	public static final String GET_COUNT_ALL_USERS = "SELECT count(u.user_id) AS all_users FROM users u WHERE u.deleted = false";
	public static final String UPDATE = "UPDATE users SET first_name = :firstName, last_name = :lastName, email = :email, password = :password, "
					+ "role_id = (SELECT r.role_id FROM role r WHERE r.name = :roleName) WHERE user_id = :id AND deleted = false";
	public static final String DELETE = "UPDATE users SET deleted = true WHERE user_id = :id";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public User create(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("roleName", user.getRole().toString());
		jdbcTemplate.update(INSERT, params, keyHolder);
		Map<String, Object> keys = keyHolder.getKeys();
		if (keys.get("user_id") == null) {
			throw new RuntimeException("Couldn't create new user");
		}
		Long id = (Long) keys.get("user_id");
		return get(id);
	}

	@Override
	public User get(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbcTemplate.queryForObject(GET_BY_ID, params, this::mapRow);
	}

	@Override
	public List<User> getAll() {
		return jdbcTemplate.query(GET_ALL, this::mapRow);
	}

	@Override
	public List<User> getAll(int limit, long offset) {
		Map<String, Object> params = new HashMap<>();
		params.put("limit", limit);
		params.put("offset", offset);
		return jdbcTemplate.query(GET_ALL_PAGED, params, this::mapRow);
	}

	@Override
	public User getUserByEmail(String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		try {
			return jdbcTemplate.queryForObject(GET_BY_EMAIL, params, this::mapRow);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<User> getUsersByLastName(String lastName) {
		Map<String, Object> params = new HashMap<>();
		params.put("lastName", lastName);
		return jdbcTemplate.query(GET_BY_EMAIL, params, this::mapRow);
	}

	@Override
	public long countAll() {
		Long count = jdbcTemplate.query(GET_COUNT_ALL_USERS, (rs) -> {
			if (rs.next()) {
				return rs.getLong("all_users");
			}
			throw new RuntimeException("ERROR: count of users not definition");
		});
		return count;
	}

	@Override
	public User update(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("roleName", user.getRole().toString());
		params.addValue("id", user.getId());
		int rowUpdated = jdbcTemplate.update(UPDATE, params);
		if (rowUpdated == 0) {
			throw new RuntimeException("Couldn't update user with id=" + user.getId());
		}
		return get(user.getId());
	}

	@Override
	public boolean delete(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbcTemplate.update(DELETE, params) == 1;
	}

	private User mapRow(ResultSet result, int num) throws SQLException {
		User user = new User();
		user.setId(result.getLong("user_id"));
		user.setFirstName(result.getString("first_name"));
		user.setLastName(result.getString("last_name"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setRole(UserRole.valueOf(result.getString("role")));
		return user;
	}
}
