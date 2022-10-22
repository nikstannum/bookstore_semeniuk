package com.belhard.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

@Controller
public class LoginCommand implements Command {
	private final UserService userService;

	public LoginCommand(UserService userService) {
		this.userService = userService;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		UserDto userDto = userService.validate(email, password);
		HttpSession session = req.getSession();
		session.setAttribute("user", userDto);
		req.setAttribute("message", "successfully login");
		return "index.jsp";
	}

}
