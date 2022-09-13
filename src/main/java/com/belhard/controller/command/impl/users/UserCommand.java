package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserCommand implements Command {
	private final UserService service;

	public UserCommand(UserService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		String idStr = req.getParameter("id");
		Long id = Long.parseLong(idStr);
		UserDto dto = service.get(id);
		req.setAttribute("user", dto);
		req.getRequestDispatcher("jsp/user.jsp");
		log.info("return page jsp/user.jsp");
		return "jsp/user/user.jsp";
	}
}
