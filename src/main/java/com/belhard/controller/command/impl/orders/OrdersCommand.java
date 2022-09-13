package com.belhard.controller.command.impl.orders;

import java.util.List;

import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrdersCommand implements Command {
	private final OrderService service;

	public OrdersCommand(OrderService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		List<OrderDto> orders = service.getAll();
		req.setAttribute("orders", orders);
		log.info("return page jsp/orders.jsp");
		return "jsp/order/orders.jsp";
	}
}
