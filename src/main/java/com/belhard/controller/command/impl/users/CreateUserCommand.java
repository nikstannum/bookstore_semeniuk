package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;
import com.belhard.dao.entity.User;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CreateUserCommand implements Command {
	
	private final UserService service;
	

	public CreateUserCommand(UserService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		UserDto userDto = new UserDto();
		String firstName = req.getParameter("firstName");
		userDto.setFirstName(firstName);
		String lastName = req.getParameter("lastName");
		userDto.setLastName(lastName);
		String email = req.getParameter("email");
		userDto.setEmail(email);
		String password = req.getParameter("password");
		userDto.setPassword(password);
		String role = req.getParameter("role");
		userDto.setUserRoleDto(UserDto.UserRoleDto.valueOf(role));
		UserDto created = service.create(userDto);
		req.setAttribute("user", created);
		req.setAttribute("message", "User created successfully");
		HttpSession session = req.getSession();
		session.setAttribute("user", created);
		return "jsp/user/user.jsp";
	}
}
