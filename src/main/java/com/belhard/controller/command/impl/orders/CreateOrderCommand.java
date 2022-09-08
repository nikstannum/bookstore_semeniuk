package com.belhard.controller.command.impl.orders;

import com.belhard.controller.command.Command;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CreateOrderCommand implements Command {

	private final OrderService orderService;

	public CreateOrderCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest req) { //FIXME
		OrderDto orderDto = new OrderDto();
		HttpSession session = req.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");

		orderService.create(null);

		return null;
	}

}
