package com.belhard.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	String execute(HttpServletRequest req);
}
