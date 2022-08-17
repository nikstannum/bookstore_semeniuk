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

public class UserDaoImpl implements UserDao {

    public static final String INSERT = "INSERT INTO users (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
    public static final String GET_BY_ID = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.user_id = ? AND u.deleted = false";
    public static final String GET_ALL = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.deleted = false";
    public static final String GET_BY_EMAIL = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.email = ? AND u.deleted = false";
    public static final String GET_BY_LASTNAME = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password, r.name AS role from users u " +
            "JOIN role r ON u.role_id = r.role_id WHERE u.lastName = ? AND u.deleted = false";
    public static final String GET_COUNT_ALL_USERS = "SELECT count(u.user_id) AS all_users from users u WHERE u.deleted = false";
    public static final String UPDATE = "UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ? WHERE user_id = ? AND deleted = false";
    public static final String DELETE = "UPDATE users SET deleted = true WHERE u.user_id = ?";

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
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong("user_id");
                return get(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User get(long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return processUser(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return processUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            while (resultSet.next()) {
                users.add(processUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int countAllUsers() {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_COUNT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int allUsers = resultSet.getInt("all_users");
                return allUsers;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("ERROR: count of users not definition");
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setLong(5, user.getId());
            return get(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDelete = statement.executeUpdate();
            return rowsDelete == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User processUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("user_id"));
        user.setFirstName(result.getString("firstName"));
        user.setLastName(result.getString("lastName"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setRole(UserRole.valueOf(result.getString("user_id")));
        return user;
    }
}