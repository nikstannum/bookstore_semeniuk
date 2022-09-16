package com.belhard.controller.command.impl.orders;

import java.util.List;

import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderInfoDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DecreaseQuantityCommand implements Command {
	private final OrderService service;

	public DecreaseQuantityCommand(OrderService service) {
		this.service = service;
	}

	@Override
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
