package com.belhard.controller.command.impl.orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

@Controller
public class UpdateOrderFormCommand implements Command {
	private final OrderService service;

	public UpdateOrderFormCommand(OrderService service) {
		this.service = service;
	}

	@LogInvocation
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
