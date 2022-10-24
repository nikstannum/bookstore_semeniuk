package com.belhard.controller.command.impl.orders;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

@Controller
public class CartCommand {
	private final OrderService orderService;
	private final String PAGE = "jsp/order/cart.jsp";

	public CartCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@LogInvocation
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		UserDto user = (UserDto) session.getAttribute("user");
		if (cart == null) {
			req.setAttribute("message", "Your cart is empty");
			return PAGE;
		}
		req.setAttribute("message", "products:");
		OrderDto processed = orderService.processCart(cart, user);
		req.setAttribute("cart", processed);
		return PAGE;
	}

}
