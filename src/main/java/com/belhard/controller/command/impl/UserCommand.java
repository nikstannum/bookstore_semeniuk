package com.belhard.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
	private final UserService service;
	private static final Logger log = LogManager.getLogger(UserCommand.class);

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
		return "jsp/user.jsp";
	}
}
