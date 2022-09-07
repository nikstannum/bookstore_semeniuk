package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;
import com.belhard.dao.entity.User;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UpdateUserCommand implements Command {
	
	private final UserService service;
	

	public UpdateUserCommand(UserService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		Long id = Long.parseLong(req.getParameter("id"));
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setFirstName(firstName);
		userDto.setLastName(lastName);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setUserRoleDto(UserDto.UserRoleDto.valueOf(role));
		UserDto updated = service.update(userDto);
		req.setAttribute("user", updated);
		String message = "User updated successfully";
		req.setAttribute("messageUpdated", message);
		return "jsp/user.jsp";
	}
}
