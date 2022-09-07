package com.belhard.controller.command.impl.users;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {

	private final UserService service;
	private static final Logger log = LogManager.getLogger(UsersCommand.class);

	public UsersCommand(UserService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		List<UserDto> users = service.getAll();
		req.setAttribute("users", users);
		log.info("return page jsp/users.jsp");
		return "jsp/users.jsp";
	}
}
