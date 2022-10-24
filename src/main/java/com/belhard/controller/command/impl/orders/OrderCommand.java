package com.belhard.controller.command.impl.orders;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

@Controller
public class OrderCommand {
	private final OrderService service;

	public OrderCommand(OrderService service) {
		this.service = service;
	}

	@LogInvocation
	public String execute(HttpServletRequest req) {
		String idStr = req.getParameter("id");
		Long id = Long.parseLong(idStr);
		OrderDto dto = service.get(id);
		req.setAttribute("order", dto);
		return "jsp/order/order.jsp";
	}
}
