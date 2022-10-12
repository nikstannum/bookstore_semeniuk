package com.belhard.controller.command.impl.orders;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IncreaseQuantityCommand implements Command {
	private final OrderService service;

	public IncreaseQuantityCommand(OrderService service) {
		this.service = service;
	}

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		Long detailsDtoId = Long.parseLong(req.getParameter("detailsDtoId"));
		boolean increase = true;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase);
		session.setAttribute("order", order);
		return "jsp/order/updateOrderForm.jsp";
	}
}
