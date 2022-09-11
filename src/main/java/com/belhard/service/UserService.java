package com.belhard.service;

import com.belhard.service.dto.UserDto;
import java.util.List;

public interface UserService extends CrudService<Long, UserDto> {

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByLastName(String lastName);

    UserDto validate(String email, String password);
    
}
