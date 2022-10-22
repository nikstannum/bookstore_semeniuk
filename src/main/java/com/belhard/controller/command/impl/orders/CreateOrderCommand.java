package com.belhard.controller.command.impl.orders;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

@Controller
public class CreateOrderCommand implements Command {

	private final OrderService orderService;

	public CreateOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		if (userDto == null) {
			req.setAttribute("message", "Please, login");
			return "jsp/loginForm.jsp";
		}
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		OrderDto orderDto = orderService.processCart(cart, userDto);
		OrderDto created = orderService.create(orderDto);
		req.setAttribute("order", created);
		req.setAttribute("message", "Order created successfully");
		session.removeAttribute("cart");
		return "jsp/order/order.jsp";
	}

}
