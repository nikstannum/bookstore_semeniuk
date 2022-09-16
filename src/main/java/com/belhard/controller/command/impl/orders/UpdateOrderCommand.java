package com.belhard.controller.command.impl.orders;

import com.belhard.controller.command.Command;
import com.belhard.dao.entity.User;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UpdateOrderCommand implements Command {
	
	private final OrderService service;

	public UpdateOrderCommand(OrderService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		HttpSession session =req.getSession();
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		service.update(orderDto);
		System.out.println("UPDATED " + orderDto); //FIXME DELETE
		session.removeAttribute("order");
		return "redirect:controller?command=orders";
	}
}
