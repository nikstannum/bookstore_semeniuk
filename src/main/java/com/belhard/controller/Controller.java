package com.belhard.controller;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.belhard.ContextConfiguration;
import com.belhard.controller.command.Command;
import com.belhard.controller.command.CommandResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("serial")
@Log4j2
@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final String REDIRECT = "redirect:";
	
	private CommandResolver resolver;
	private AnnotationConfigApplicationContext context;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String commandStr = req.getParameter("command");
		Class<? extends Command> commandDefinition = resolver.getCommand(commandStr);
		Command command = context.getBean(commandDefinition);
		String page = command.execute(req);
		if (page.startsWith(REDIRECT)) {
			resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
		} else {
			req.getRequestDispatcher(page).forward(req, resp);
		}
	}

	@Override
	public void destroy() {
		context.close();
		log.info("SERVLET DESTROYED");
	}

	@Override
	public void init() throws ServletException {
		context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
		resolver = context.getBean(CommandResolver.class);
		log.info("SERVLET INIT");
	}
	
	

}
