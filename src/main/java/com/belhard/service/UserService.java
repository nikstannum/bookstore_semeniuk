package com.belhard.service;

import java.util.List;
import java.util.Locale;

import com.belhard.service.dto.UserDto;

public interface UserService extends CrudService<Long, UserDto> {

	UserDto getUserByEmail(String email, Locale locale);

	List<UserDto> getUsersByLastName(String lastName, Locale locale);

	UserDto validate(String email, String password,  Locale locale);

}
