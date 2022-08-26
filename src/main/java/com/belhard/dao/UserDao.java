package com.belhard.dao;

import com.belhard.dao.entity.User;

import java.util.List;

public interface UserDao extends CrudDao<Long, User> {
    User getUserByEmail(String email);

    List<User> getUsersByLastName(String lastName);
}
