package com.belhard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belhard.dao.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User getUserByEmail(String email);

	List<User> getUsersByLastName(String lastName);
}
