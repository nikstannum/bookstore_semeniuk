package com.belhard.service;

import com.belhard.service.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto getById(long id);

    List<UserDto> getAll();

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByLastName(String lastName);

    int countAllUsers();

    UserDto update(UserDto userDto);

    boolean delete(long id);

    boolean validate(String email, String password);
}
