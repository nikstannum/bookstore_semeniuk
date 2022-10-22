//package com.belhard.controller;
//
//import java.io.IOException;
//
//import com.belhard.controller.command.Command;
//import com.belhard.controller.command.CommandResolver;
//import com.belhard.controller.command.impl.errors.ErrorCommand;
//import com.belhard.exception.MyAppException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@SuppressWarnings("serial")
//@WebServlet("/controller")
//public class Controller extends HttpServlet {
//
//	private static final String REDIRECT = "redirect:";
//
//	private CommandResolver resolver;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//
//	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String commandStr = req.getParameter("command");
//		Class<? extends Command> commandDefinition = resolver.getCommand(commandStr);
//		Command command = ContextListener.context.getBean(commandDefinition);
//		String page;
//		try {
//			page = command.execute(req);
//		} catch (MyAppException e) {
//			command = ContextListener.context.getBean(ErrorCommand.class);
//			String message = e.getMessage();
//			req.setAttribute("message", message);
//			page = command.execute(req);
//		}
//
//		if (page.startsWith(REDIRECT)) {
//			resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
//		} else {
//			req.getRequestDispatcher(page).forward(req, resp);
//		}
//	}
//
//	@Override
//	public void destroy() {
//	}
//
//	@Override
//	public void init() throws ServletException {
//		resolver = ContextListener.context.getBean(CommandResolver.class);
//	}
//
//}
