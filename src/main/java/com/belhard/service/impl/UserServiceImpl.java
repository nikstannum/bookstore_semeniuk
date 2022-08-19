package com.belhard.service.impl;

import com.belhard.dao.UserDao;
import com.belhard.dao.entity.User;
import com.belhard.dao.entity.User.UserRole;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import com.belhard.service.dto.UserDto.UserRoleDto;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        logger.debug("Service method called successfully");
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
        }
        User user = toEntity(userDto);
        return toDto(userDao.create(user));
    }

    private User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(UserRole.valueOf(userDto.getUserRoleDto().toString()));
        return user;
    }

    @Override
    public UserDto getById(long id) {
        logger.debug("Service method called successfully");
        User user = userDao.get(id);
        return toDto(user);
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUserRoleDto(UserRoleDto.valueOf(user.getRole().toString()));
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        logger.debug("Service method called successfully");
        return userDao.getAll().stream().map(this::toDto).toList();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("Service method called successfully");
        User user = userDao.getUserByEmail(email);
        return toDto(user);
    }

    @Override
    public List<UserDto> getUsersByLastName(String lastName) {
        logger.debug("Service method called successfully");
        return userDao.getUsersByLastName(lastName).stream().map(this::toDto).toList();
    }

    @Override
    public int countAllUsers() {
        logger.debug("Service method called successfully");
        return userDao.countAllUsers();
    }

    @Override
    public UserDto update(UserDto userDto) {
        logger.debug("Service method called successfully");
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null && existing.getId() != userDto.getId()) {
            throw new RuntimeException("User with email = " + userDto.getEmail() + " already exists");
        }
        User user = toEntity(userDto);
        return toDto(userDao.update(user));
    }

    @Override
    public boolean delete(long id) {
        logger.debug("Service method called successfully");
        return userDao.delete(id);
    }

    @Override
    public boolean validate(String email, String password) {
        logger.debug("Service method called successfully");
        User user = userDao.getUserByEmail(email);
        String userPassword = user.getPassword();
        return userPassword.equals(password);
    }
}
