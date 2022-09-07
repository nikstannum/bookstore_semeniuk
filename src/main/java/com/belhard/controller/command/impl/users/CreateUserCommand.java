package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;
import com.belhard.dao.entity.User;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class CreateUserCommand implements Command {
	
	private final UserService service;
	

	public CreateUserCommand(UserService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		UserDto userDto = new UserDto();
		userDto.setFirstName(firstName);
		userDto.setLastName(lastName);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setUserRoleDto(UserDto.UserRoleDto.valueOf(role));
		UserDto created = service.create(userDto);
		req.setAttribute("user", created);
		String message = "User created successfully";
		req.setAttribute("messageCreated", message);
		return "jsp/user.jsp";
	}
}
