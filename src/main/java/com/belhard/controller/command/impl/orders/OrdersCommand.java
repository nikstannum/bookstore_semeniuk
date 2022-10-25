package com.belhard.controller.command.impl.orders;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderDto.StatusDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;

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

	@RequestMapping("/{id}")
	@LogInvocation
	public String orderById(@PathVariable Long id, Model model) {
		OrderDto dto = service.get(id);
		model.addAttribute("order", dto);
		return "order/order";
	}

	@RequestMapping("/update")
	@LogInvocation
	public String updateForm(HttpSession session, @RequestParam Long id) {
		OrderDto order = service.get(id);
		session.setAttribute("order", order);
		session.setAttribute("status", order.getStatusDto());
		return "order/updateOrderForm";
	}

	@RequestMapping("/decrease_quantity")
	@LogInvocation
	public String decrease(HttpSession session, @RequestParam Long detailsDtoId) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		boolean increase = false;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase);
		session.setAttribute("order", order);
		return "order/updateOrderForm";
	}

	@RequestMapping("/increase_quantity")
	@LogInvocation
	public String increase(HttpSession session, @RequestParam Long detailsDtoId) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		boolean increase = true;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase);
		session.setAttribute("order", order);
		return "order/updateOrderForm";
	}

	@RequestMapping("/update_order")
	@LogInvocation
	public String updateOrder(HttpSession session, @RequestParam StatusDto statusDto) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		orderDto.setStatusDto(statusDto);
		service.update(orderDto);
		session.removeAttribute("order");
		return "redirect:all";
	}
	
	@RequestMapping("/checkout")
	@LogInvocation
	public String checkoutOrder(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		if (userDto == null) {
			model.addAttribute("message", "Please, login");
			return "user/loginForm";
		}
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		OrderDto orderDto = service.processCart(cart, userDto);
		OrderDto created = service.create(orderDto);
		req.setAttribute("order", created);
		req.setAttribute("message", "Order created successfully");
		session.removeAttribute("cart");
		return "order/order";
	}

}
