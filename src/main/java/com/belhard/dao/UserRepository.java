package com.belhard.dao;

import java.util.List;
import java.util.Optional;

import com.belhard.dao.entity.User;

public interface UserRepository extends CrudRepository<Long, User> {
	Optional<User> getUserByEmail(String email);

	List<User> getUsersByLastName(String lastName);
}
