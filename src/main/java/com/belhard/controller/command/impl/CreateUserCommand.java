package com.belhard.controller.command.impl;

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
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		UserDto userDto = new UserDto();
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setUserRoleDto(UserDto.UserRoleDto.USER);
		UserDto created = service.create(userDto);
		req.setAttribute("user", created);
		return "jsp/user.jsp";
	}
}
