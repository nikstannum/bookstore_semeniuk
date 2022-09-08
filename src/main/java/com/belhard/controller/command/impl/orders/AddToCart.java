package com.belhard.controller.command.impl.orders;

import java.util.HashMap;
import java.util.Map;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AddToCart implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		Long bookId = Long.parseLong(req.getParameter("bookId"));
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		Integer quantuty = cart.get(bookId);
		if (quantuty == null) {
			cart.put(bookId, 1);
		} else {
			cart.put(bookId, quantuty + 1);
		}
		session.setAttribute("cart", cart);
		return "jsp/books.jsp";
	}

}
