package com.belhard.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.belhard.aop.LogInvocation;

@SuppressWarnings("serial")
@WebFilter(urlPatterns = "/*")
@Component
public class AuthorizationFilter extends HttpFilter {

	@Override
	@LogInvocation
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
