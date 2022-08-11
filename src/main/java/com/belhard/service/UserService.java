package main.java.com.belhard.service;

import main.java.com.belhard.service.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto get(long id);

    List<UserDto> getAll();

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByLastName(String lastName);

    int countAllUsers();

    UserDto update(UserDto userDto);

    boolean delete(long id);

    boolean validate(String email, String password);
}
