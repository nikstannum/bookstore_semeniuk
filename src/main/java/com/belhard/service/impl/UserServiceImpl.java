package com.belhard.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	private final DigestUtil digestUtil;

	@Override
	@LogInvocation
	public UserDto create(UserDto userDto) {
		validatePassword(userDto);
		String hashedPassword = digestUtil.hash(userDto.getPassword());
		userDto.setPassword(hashedPassword);
		User existing = userRepository.getUserByEmail(userDto.getEmail()); // TODO Optional?
		if (existing != null) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = mapper.userToEntity(userDto);
		User created = userRepository.save(user);
		return mapper.userToDto(created);
	}

	private void validatePassword(UserDto userDto) {
		if (userDto.getPassword().length() < 4) {
			throw new RuntimeException("Password is too short. Password length have to more than three symbols");
		}
	}

	@LogInvocation
	@Override
	public UserDto get(Long id) {
		User user = userRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("user with id = " + id + " wasn't found"));
		return mapper.userToDto(user);
	}

	@LogInvocation
	@Override
	public List<UserDto> getAll() {
		return userRepository.findAll().stream().map(mapper::userToDto).toList();
	}

	@LogInvocation
	@Override
	public List<UserDto> getAll(Paging paging) {
		int page = (int) paging.getPage();
		int limit = paging.getLimit();
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Page<User> userPage = userRepository.findAll(pageable);
		return userPage.toList().stream().map(mapper::userToDto).toList();
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
		return userRepository.getUsersByLastName(lastName).stream().map(mapper::userToDto).toList();
	}

	@LogInvocation
	@Override
	public long countAll() { // TODO Check work on @Where deleted = false
		return userRepository.count();
	}

	@LogInvocation
	@Override
	public UserDto update(UserDto userDto) {
		User existing = userRepository.getUserByEmail(userDto.getEmail());
		if (existing != null && !(existing.getId().equals(userDto.getId()))) {
			throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
		}
		User user = mapper.userToEntity(userDto);
		return mapper.userToDto(userRepository.save(user));
	}

	@LogInvocation
	@Override
	public void delete(Long id) {
		User user = userRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("user with id = " + id + " wasn't found"));
		user.setDeleted(true);
		userRepository.save(user);
	}

	@LogInvocation
	@Override
	public UserDto validate(String email, String password) {
		String hashedPassword = digestUtil.hash(password);
		User user = userRepository.getUserByEmail(email);
		String userPassword = user.getPassword();
		if (userPassword.equals(hashedPassword)) {
			return getUserByEmail(email);
		}
		throw new RuntimeException("Wrong email or password");
	}
}
