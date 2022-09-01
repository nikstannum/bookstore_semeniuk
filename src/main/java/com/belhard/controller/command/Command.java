package com.belhard.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
	String execute(HttpServletRequest req);
}
