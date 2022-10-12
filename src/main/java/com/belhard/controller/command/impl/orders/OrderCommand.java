package com.belhard.controller.command.impl.orders;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderCommand implements Command {
	private final OrderService service;

	public OrderCommand(OrderService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		String idStr = req.getParameter("id");
		Long id = Long.parseLong(idStr);
		OrderDto dto = service.get(id);
		req.setAttribute("order", dto);
		return "jsp/order/order.jsp";
	}
}
