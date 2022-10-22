package com.belhard.controller.command.impl.users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

@Controller
public class UpdateUserFormCommand implements Command {
	private final UserService service;

	public UpdateUserFormCommand(UserService service) {
		this.service = service;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		Long id = Long.parseLong(req.getParameter("id"));
		UserDto userDto = service.get(id);
		req.setAttribute("user", userDto);
		return "jsp/user/updateUserForm.jsp";
	}
}
