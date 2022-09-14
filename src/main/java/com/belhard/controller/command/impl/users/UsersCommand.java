package com.belhard.controller.command.impl.users;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.UserService;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

public class UsersCommand implements Command {

	private final UserService service;
	private final PagingUtil pagingUtil;

	public UsersCommand(UserService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@Override
	public String execute(HttpServletRequest req) {
		Paging paging = pagingUtil.getPaging(req);
		List<UserDto> users = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		req.setAttribute("users", users);
		req.setAttribute("currentCommand", "users");
		req.setAttribute("currentPage", paging.getPage());
		req.setAttribute("totalPages", totalPages);
		return "jsp/user/users.jsp";
	}
}
