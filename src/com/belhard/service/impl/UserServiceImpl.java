package com.belhard.service.impl;

import com.belhard.dao.UserDao;
import com.belhard.dao.entity.User;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = toEntity(userDto);
        return get(user.getId());
    }

    private User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    @Override
    public UserDto get(long id) {
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
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(this::toDto).toList();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        return toDto(user);
    }

    @Override
    public List<UserDto> getUsersByLastName(String lastName) {
        return userDao.getUsersByLastName(lastName).stream().map(this::toDto).toList();
    }

    @Override
    public int countAllUsers() {
        return userDao.countAllUsers();
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = toEntity(userDto);
        return get(user.getId());
    }

    @Override
    public boolean delete(long id) {
        return userDao.delete(id);
    }

    public boolean validate(String email, String password) {
        User user = userDao.getUserByEmail(email);
        String userPassword = user.getPassword();
        return userPassword.equals(password);
    }
}
