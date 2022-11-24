package com.belhard.service.impl;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.UserRepository;
import com.belhard.dao.entity.User;
import com.belhard.exception.EntityNotFoundException;
import com.belhard.exception.SuchEntityExistsException;
import com.belhard.exception.WrongValueException;
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
	private final PasswordEncoder passwordEncoder;
	private final MessageSource messageSource;

	@Override
	@LogInvocation
	public UserDto create(UserDto userDto) throws SuchEntityExistsException {
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(hashedPassword);
		User existing = userRepository.getUserByEmail(userDto.getEmail());
		if (existing != null) {
			Object[] args = new Object[] { userDto.getEmail() };
			String message = messageSource.getMessage("user.error.already_exists", args,
							LocaleContextHolder.getLocale());
			throw new SuchEntityExistsException(message);
		}
		User user = mapper.userToEntity(userDto);
		User created = userRepository.save(user);
		return mapper.userToDto(created);
	}

	@LogInvocation
	@Override
	public UserDto get(Long id) throws EntityNotFoundException {
		Object[] args = new Object[] { id };
		String message = messageSource.getMessage("user.error.not_found_by_id", args, LocaleContextHolder.getLocale());
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
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
	public UserDto getUserByEmail(String email) throws EntityNotFoundException {
		User user = userRepository.getUserByEmail(email);
		if (user == null) {
			String message = messageSource.getMessage("user.error.not_found_by_email", new Object[] { email },
							LocaleContextHolder.getLocale());
			throw new EntityNotFoundException(message);
		}
		return mapper.userToDto(user);
	}

	@LogInvocation
	@Override
	public List<UserDto> getUsersByLastName(String lastName) {
		return userRepository.getUsersByLastName(lastName).stream().map(mapper::userToDto).toList();
	}

	@LogInvocation
	@Override
	public long countAll() {
		return userRepository.count();
	}

	@LogInvocation
	@Override
	public UserDto update(UserDto userDto) throws SuchEntityExistsException {
		User existing = userRepository.getUserByEmail(userDto.getEmail());
		if (existing != null && !(existing.getId().equals(userDto.getId()))) {
			String message = messageSource.getMessage("user.error.already_exists", new Object[] { userDto.getEmail() },
							LocaleContextHolder.getLocale());
			throw new SuchEntityExistsException(message);
		}
		User user = mapper.userToEntity(userDto);
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(hashedPassword);
		return mapper.userToDto(userRepository.save(user));
	}

	@LogInvocation
	@Override
	public void delete(Long id) throws EntityNotFoundException {
		String message = messageSource.getMessage("user.error.not_found_by_id", new Object[] { id },
						LocaleContextHolder.getLocale());
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
		user.setDeleted(true);
		userRepository.save(user);
	}

	@LogInvocation
	@Override
	public UserDto validate(String email, String password) throws WrongValueException {
		String message = messageSource.getMessage("user.security.wrong_email_password", null,
						LocaleContextHolder.getLocale());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new WrongValueException(message);
		}
		User user = userRepository.getUserByEmail(email);
		boolean correctPassword = passwordEncoder.matches(password, user.getPassword());
		if (user == null || !correctPassword) {
			throw new WrongValueException(message);
		}
		return getUserByEmail(email);
	}

	@Override
	public Page<UserDto> getAll(Pageable pageable) { // FIXME
		// TODO Auto-generated method stub
		return null;
	}
}
