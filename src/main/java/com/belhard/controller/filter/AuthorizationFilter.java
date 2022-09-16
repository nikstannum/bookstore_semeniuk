package com.belhard.controller.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebFilter(urlPatterns = "/*")
public class AuthorizationFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String command = req.getParameter("command");
		if (requiresAuthorization(command)) {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				req.setAttribute("message", "Require authorization");
				req.getRequestDispatcher("jsp/loginForm.jsp").forward(req, res);
				return;
			}
		}
		chain.doFilter(req, res);
	}

	private boolean requiresAuthorization(String command) {
		if (command == null) {
			return false;
		}
		return switch (command) {
		case "users", "orders" -> true;
		default -> false;
		};
	}
}
