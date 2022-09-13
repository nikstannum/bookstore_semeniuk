package com.belhard.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.UserDao;
import com.belhard.dao.entity.User;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

public class UserServiceImpl implements UserService {


	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	private final UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDto create(UserDto userDto) {
		log.debug("Service method called successfully");
		User existing = userDao.getUserByEmail(userDto.getEmail());
		if (existing != null) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = toEntity(userDto);
		return toDto(userDao.create(user));
	}

	private User toEntity(UserDto userDto) {
		User user = Mapper.INSTANCE.userToEntity(userDto);
		return user;
	}

	@Override
	public UserDto get(Long id) {
		log.debug("Service method called successfully");
		User user = userDao.get(id);
		return toDto(user);
	}

	private UserDto toDto(User user) {
		UserDto userDto = Mapper.INSTANCE.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAll() {
		log.debug("Service method called successfully");
		return userDao.getAll().stream().map(this::toDto).toList();
	}
	
	@Override
	public List<UserDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		log.debug("Service method called successfully");
		return userDao.getAll(limit, offset).stream().map(this::toDto).toList();
	}

	@Override
	public UserDto getUserByEmail(String email) {
		log.debug("Service method called successfully");
		User user = userDao.getUserByEmail(email);
		return toDto(user);
	}

	@Override
	public List<UserDto> getUsersByLastName(String lastName) {
		log.debug("Service method called successfully");
		return userDao.getUsersByLastName(lastName).stream().map(this::toDto).toList();
	}

	@Override
	public long countAll() {
		log.debug("Service method called successfully");
		return userDao.countAll();
	}

	@Override
	public UserDto update(UserDto userDto) {
		log.debug("Service method called successfully");
		User existing = userDao.getUserByEmail(userDto.getEmail());
		if (existing != null && !(existing.getId().equals(userDto.getId()))) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = toEntity(userDto);
		return toDto(userDao.update(user));
	}

	@Override
	public void delete(Long id) {
		log.debug("Service method called successfully");
		userDao.delete(id);
	}

	@Override
	public UserDto validate(String email, String password) {
		log.debug("Service method called successfully");
		User user = userDao.getUserByEmail(email);
		String userPassword = user.getPassword();
		if (userPassword.equals(password)) {
			return getUserByEmail(email);
		}
		throw new RuntimeException("Wrong email or password");
	}
}
