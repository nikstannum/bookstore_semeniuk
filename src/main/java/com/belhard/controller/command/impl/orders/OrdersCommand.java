package com.belhard.controller.command.impl.orders;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;

@Controller
@RequestMapping("orders")
public class OrdersCommand {
	private final OrderService service;
	private final PagingUtil pagingUtil;

	public OrdersCommand(OrderService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@LogInvocation
	@RequestMapping("/all")
	public String allOrders(@RequestParam(defaultValue = "10") Integer limit,
					@RequestParam(defaultValue = "1") Long page, Model model) {
		
		Paging paging = pagingUtil.getPaging(limit, page);
		List<OrderDto> orders = service.getAll(paging);

		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		model.addAttribute("orders", orders);
		model.addAttribute("currentCommand", "orders");
		model.addAttribute("currentPage", paging.getPage());
		model.addAttribute("totalPages", totalPages);
		return "order/orders";
	}
}
