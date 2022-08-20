package com.belhard.dao.impl;

import com.belhard.dao.UserDao;
import com.belhard.dao.connection.DataSource;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl implements UserDao {

    private static Logger logger = LogManager.getLogger(UserDaoImpl.class);//FIXME final, rename to 'log'

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
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            logger.debug("database access completed successfully");
            if (keys.next()) {
                Long id = keys.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public User get(Long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            logger.debug("database access completed successfully");
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }



    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_ALL);
            ResultSet resultSet = statement.executeQuery();
            logger.debug("database access completed successfully");
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            logger.debug("database access completed successfully");
            if (resultSet.next()) {
                return processUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_LASTNAME);
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            logger.debug("database access completed successfully");
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    @Override
    public int countAll() {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_COUNT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            logger.debug("database access completed successfully");
            if (resultSet.next()) {
                int allUsers = resultSet.getInt("all_users");
                return allUsers;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        throw new RuntimeException("ERROR: count of users not definition");
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            logger.debug("database access completed successfully");
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDelete = statement.executeUpdate();
            logger.debug("database access completed successfully");
            return rowsDelete == 1;
        } catch (SQLException e) {
            logger.error(e);
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
