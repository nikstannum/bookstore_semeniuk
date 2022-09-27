package com.belhard.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.belhard.controller.command.Command;
import com.belhard.controller.command.CommandResolver;
import com.belhard.dao.connection.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("serial")
@Log4j2
@org.springframework.stereotype.Controller
@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final String REDIRECT = "redirect:";
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String command = req.getParameter("command");
		CommandResolver commandResolver = new CommandResolver();
		Command commandInstance = commandResolver.getCommand(command);
		String page = commandInstance.execute(req);
		if (page.startsWith(REDIRECT)) {
			resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
		} else {
			req.getRequestDispatcher(page).forward(req, resp);
		}
	}

	@Override
	public void destroy() {
		try {
			dataSource.close();
			log.info("dataSource destroyed");
		} catch (Exception e) {
			log.error("dataSource didn't destroy", e);
		}
	}
}
