package com.belhard.controller.command.impl.orders;

import java.util.List;

import com.belhard.controller.command.Command;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrdersCommand implements Command {
	private final OrderService service;
	private final PagingUtil pagingUtil;

	public OrdersCommand(OrderService service, PagingUtil pagingUtil) {
		super();
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@Override
	public String execute(HttpServletRequest req) {
		Paging paging = PagingUtil.INSTANCE.getPaging(req);
		List<OrderDto> orders = service.getAll(paging);
		
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		req.setAttribute("orders", orders);
		req.setAttribute("currentCommand", "orders");
		req.setAttribute("currentPage", paging.getPage());
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("orders", orders);
		return "jsp/order/orders.jsp";
	}
}
