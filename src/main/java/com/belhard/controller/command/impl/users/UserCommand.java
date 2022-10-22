package com.belhard.controller.command.impl.users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

@Controller
public class UserCommand implements Command {
	private final UserService service;

	public UserCommand(UserService service) {
		this.service = service;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		String idStr = req.getParameter("id");
		Long id = Long.parseLong(idStr);
		UserDto dto = service.get(id);
		req.setAttribute("user", dto);
		req.getRequestDispatcher("jsp/user.jsp");
		return "jsp/user/user.jsp";
	}
}
