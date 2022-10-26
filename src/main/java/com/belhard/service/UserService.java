package com.belhard.service;

import java.util.List;

import com.belhard.service.dto.UserDto;

public interface UserService extends CrudService<Long, UserDto> {

	UserDto getUserByEmail(String email);

	List<UserDto> getUsersByLastName(String lastName);

	UserDto validate(String email, String password);

}
