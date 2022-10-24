package com.belhard.controller.command.impl.orders;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;

@Controller
public class DecreaseQuantityCommand {
	private final OrderService service;

	public DecreaseQuantityCommand(OrderService service) {
		this.service = service;
	}

	@LogInvocation
	public String execute(HttpServletRequest req) {
		HttpSession session = req.getSession();
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		Long detailsDtoId = Long.parseLong(req.getParameter("detailsDtoId"));
		boolean increase = false;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase);
		session.setAttribute("order", order);
		return "jsp/order/updateOrderForm.jsp";
	}
}
