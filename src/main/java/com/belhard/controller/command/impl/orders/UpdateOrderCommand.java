package com.belhard.controller.command.impl.orders;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderDto.StatusDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateOrderCommand implements Command {

	private final OrderService service;

	public UpdateOrderCommand(OrderService service) {
		this.service = service;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		String statusStr = req.getParameter("status");
		StatusDto status = StatusDto.valueOf(statusStr);
		orderDto.setStatusDto(status);
		service.update(orderDto);
		session.removeAttribute("order");
		return "redirect:controller?command=orders";
	}
}
