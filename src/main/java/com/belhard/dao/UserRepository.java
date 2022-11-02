package com.belhard.dao;

import java.util.List;

import com.belhard.dao.entity.User;

public interface UserRepository extends CrudRepository<Long, User> {
	User getUserByEmail(String email);

	List<User> getUsersByLastName(String lastName);
}
