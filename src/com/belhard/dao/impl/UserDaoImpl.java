package com.belhard.dao.impl;

import com.belhard.dao.UserDao;
import com.belhard.dao.entity.User;
import java.util.List;

public class UserDaoImpl implements UserDao {

    public static final String INSERT = "INSERT INTO users (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
    public static final String GET_BY_ID = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password from users u WHERE u.user_id = ?";
    public static final String GET_ALL = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password from users u";
    public static final String GET_BY_EMAIL = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password from users u WHERE u.email = ?";
    public static final String GET_BY_LASTNAME = "SELECT u.user_id, u.firstName, u.lastName, u.email, u.password from users u WHERE u.lastName = ?";
    public static final String GET_COUNT_ALL_USERS = "SELECT count(u.user_id) AS all_users from users u";
    public static final String UPDATE = "UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ? WHERE user_id = ?";
    public static final String DELETE = "DELETE FROM users u WHERE u.user_id = ?";

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getUsersByLastName(String lastName) {
        return null;
    }

    @Override
    public int countAllUsers() {
        return 0;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
