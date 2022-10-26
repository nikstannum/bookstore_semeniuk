package com.belhard.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.belhard.aop.LogInvocation;

@SuppressWarnings("serial")
public class AuthorizationFilter extends HttpFilter {

	@Override
	@LogInvocation
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
					throws IOException, ServletException {
		String uri = req.getServletPath();

		if (requiresAuthorization(uri)) {
			HttpSession session = req.getSession(false);
			if (session == null || session.getAttribute("user") == null) {
				req.setAttribute("message", "Require authorization");
				res.sendRedirect("../users/login_form");
				return;
			}
		}
		chain.doFilter(req, res);
	}

	private boolean requiresAuthorization(String uri) {

		if (uri == null) {
			return false;
		}
		if (uri.contains("create_user") || uri.contains("login") || uri.contains("logout") || uri.contains("cart")) {
			return false;
		}
		return true;
	}
}
