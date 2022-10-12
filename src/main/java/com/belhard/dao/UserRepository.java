package com.belhard.dao;

import com.belhard.dao.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<Long, User> {
    User getUserByEmail(String email);

    List<User> getUsersByLastName(String lastName);
}
