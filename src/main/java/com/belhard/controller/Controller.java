package com.belhard.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.controller.command.CommandFactory;
import com.belhard.dao.connection.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final Logger log = LogManager.getLogger(Controller.class);

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
		CommandFactory factory = CommandFactory.getINSTANCE();
		Command commandInstance = factory.getCommand(command);
		String page = commandInstance.execute(req);
		req.getRequestDispatcher(page).forward(req, resp);
	}

	@Override
	public void destroy() {
		try {
			DataSource.INSTANCE.close();
			log.info("dataSource destroyed");
		} catch (Exception e) {
			log.error("dataSource didn't destroy", e);
		}
	}
}
