package com.belhard.dao.impl;

import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
public class UserDaoImpl implements UserDao {

    public static final String INSERT = "INSERT INTO users (first_name, last_name, email, password, role_id) " +
            "VALUES (?, ?, ?, ?, (SELECT r.role_id FROM role r WHERE r.name = ?))";
    public static final String GET_BY_ID = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.user_id = ? AND u.deleted = false";
    public static final String GET_ALL = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.deleted = false";
    public static final String GET_BY_EMAIL = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.email = ? AND u.deleted = false";
    public static final String GET_BY_LASTNAME = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.last_name = ? AND u.deleted = false";
    public static final String GET_COUNT_ALL_USERS = "SELECT count(u.user_id) AS all_users from users u WHERE u.deleted = false";
    public static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, " +
            "role_id = (SELECT r.role_id FROM role r WHERE r.name = ?) WHERE user_id = ? AND deleted = false";
    public static final String DELETE = "UPDATE users SET deleted = true WHERE user_id = ?";

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User create(User user) {
        try (Connection connection = dataSource.getFreeConnections();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            log.debug("database access completed successfully");
            if (keys.next()) {
                Long id = keys.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        return null;
    }

    @Override
    public User get(Long id) {
        try (Connection connection = dataSource.getFreeConnections();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            log.debug("database access completed successfully");
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        return null;
    }


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getFreeConnections();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            log.debug("database access completed successfully");
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection connection = dataSource.getFreeConnections();
             PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            log.debug("database access completed successfully");
            if (resultSet.next()) {
                return processUser(resultSet);
            }
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        return null;
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getFreeConnections();
             PreparedStatement statement = connection.prepareStatement(GET_BY_LASTNAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            log.debug("database access completed successfully");
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        return users;
    }

    @Override
    public int countAll() {
        try (Connection connection = dataSource.getFreeConnections();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_COUNT_ALL_USERS);
            log.debug("database access completed successfully");
            if (resultSet.next()) {
                return resultSet.getInt("all_users");
            }
        } catch (SQLException e) {
            log.error("database access completed unsuccessfully", e);
        }
        throw new RuntimeException("ERROR: count of users not definition");
    }

    @Override
    public User update(User user) {
        try (Connection connection = dataSource.getFreeConnections();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            log.debug("database access completed successfully");
            return get(user.getId());
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

    private User processUser(ResultSet result) throws SQLException {
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
