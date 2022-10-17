package com.belhard.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.UserRepository;
import com.belhard.dao.entity.User;
import com.belhard.service.DigestUtil;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Mapper mapper;

	@LogInvocation
	@Override
	public UserDto create(UserDto userDto) {
		validatePassword(userDto);
		String hashedPassword = DigestUtil.INSTANCE.hash(userDto.getPassword());
		userDto.setPassword(hashedPassword);
		User existing = userRepository.getUserByEmail(userDto.getEmail());
		if (existing != null) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = mapper.userToEntity(userDto);
		return mapper.userToDto(userRepository.create(user));
	}

	private void validatePassword(UserDto userDto) {
		if (userDto.getPassword().length() < 4) {
			throw new RuntimeException("Password is too short. Password length have to more than three symbols");
		}
	}

	@LogInvocation
	@Override
	public UserDto get(Long id) {
		User user = userRepository.get(id);
		return mapper.userToDto(user);
	}

	@LogInvocation
	@Override
	public List<UserDto> getAll() {
		return userRepository.getAll().stream().map(o -> mapper.userToDto(o)).toList();
	}

	@LogInvocation
	@Override
	public List<UserDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		return userRepository.getAll(limit, offset).stream().map(o -> mapper.userToDto(o)).toList();
	}

	@LogInvocation
	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.getUserByEmail(email);
		return mapper.userToDto(user);
	}

	@LogInvocation
	@Override
	public List<UserDto> getUsersByLastName(String lastName) {
		return userRepository.getUsersByLastName(lastName).stream().map(o -> mapper.userToDto(o)).toList();
	}

	@LogInvocation
	@Override
	public long countAll() {
		return userRepository.countAll();
	}

	@LogInvocation
	@Override
	public UserDto update(UserDto userDto) {
		User existing = userRepository.getUserByEmail(userDto.getEmail());
		if (existing != null && !(existing.getId().equals(userDto.getId()))) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = mapper.userToEntity(userDto);
		return mapper.userToDto(userRepository.update(user));
	}

	@LogInvocation
	@Override
	public void delete(Long id) {
		userRepository.softDelete(id);
	}

	@LogInvocation
	@Override
	public UserDto validate(String email, String password) {
		String hashedPassword = DigestUtil.INSTANCE.hash(password);
		User user = userRepository.getUserByEmail(email);
		String userPassword = user.getPassword();
		if (userPassword.equals(hashedPassword)) {
			return getUserByEmail(email);
		}
		throw new RuntimeException("Wrong email or password");
	}
}
