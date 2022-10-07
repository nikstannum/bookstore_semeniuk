package com.belhard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.UserRepository;
import com.belhard.dao.entity.User;
import com.belhard.service.DigestUtil;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userDao;

	@Autowired
	public UserServiceImpl(UserRepository userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDto create(UserDto userDto) {
		validatePassword(userDto);
		String hashedPassword = DigestUtil.INSTANCE.hash(userDto.getPassword());
		userDto.setPassword(hashedPassword);
		User existing = userDao.getUserByEmail(userDto.getEmail());
		if (existing != null) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = toEntity(userDto);
		log.debug("Service method called successfully");
		return toDto(userDao.create(user));
	}

	private void validatePassword(UserDto userDto) {
		if (userDto.getPassword().length() < 4) {
			throw new RuntimeException("Password is too short. Password length have to more than three symbols");
		}
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
		String hashedPassword = DigestUtil.INSTANCE.hash(password);
		User user = userDao.getUserByEmail(email);
		String userPassword = user.getPassword();
		if (userPassword.equals(hashedPassword)) {
			return getUserByEmail(email);
		}
		throw new RuntimeException("Wrong email or password");
	}
}
