package com.belhard.controller.command.impl.orders;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateOrderFormCommand implements Command {
	private final OrderService service;

	public UpdateOrderFormCommand(OrderService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Long id = Long.parseLong(req.getParameter("id"));
		OrderDto order = service.get(id);
		session.setAttribute("order", order);
		session.setAttribute("status", order.getStatusDto());
		return "jsp/order/updateOrderForm.jsp";
	}
}
