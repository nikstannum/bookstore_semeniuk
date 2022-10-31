package com.belhard.controller.command.impl.orders;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.exception.MyAppException;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.OrderDto.StatusDto;
import com.belhard.service.dto.OrderInfoDto;
import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("orders")
public class OrdersCommand {
	private final OrderService service;
	private final PagingUtil pagingUtil;
	private final MessageSource messageSource;


	@LogInvocation
	@RequestMapping("/all")
	public String allOrders(@RequestParam(defaultValue = "10") Integer limit,
					@RequestParam(defaultValue = "1") Long page, Model model, Locale locale) {
		Paging paging = pagingUtil.getPaging(limit, page);
		List<OrderDto> orders = service.getAll(paging, locale);
		long totalEntities = service.countAll(locale);
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		model.addAttribute("orders", orders);
		model.addAttribute("currentCommand", "orders");
		model.addAttribute("currentPage", paging.getPage());
		model.addAttribute("totalPages", totalPages);
		return "order/orders";
	}

	@RequestMapping("/{id}")
	@LogInvocation
	public String orderById(@PathVariable Long id, Model model, Locale locale) {
		OrderDto dto = service.get(id, locale);
		model.addAttribute("order", dto);
		return "order/order";
	}

	@RequestMapping("/update")
	@LogInvocation
	public String updateForm(HttpSession session, @RequestParam Long id, Locale locale) {
		OrderDto order = service.get(id, locale);
		session.setAttribute("order", order);
		session.setAttribute("status", order.getStatusDto());
		return "order/updateOrderForm";
	}

	@RequestMapping("/decrease_quantity")
	@LogInvocation
	public String decrease(HttpSession session, @RequestParam Long detailsDtoId, Locale locale) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		boolean increase = false;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase, locale);
		session.setAttribute("order", order);
		return "order/updateOrderForm";
	}

	@RequestMapping("/increase_quantity")
	@LogInvocation
	public String increase(HttpSession session, @RequestParam Long detailsDtoId, Locale locale) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		List<OrderInfoDto> infosDto = orderDto.getDetailsDto();
		boolean increase = true;
		OrderDto order = service.preProcessUpdate(orderDto, infosDto, detailsDtoId, increase, locale);
		session.setAttribute("order", order);
		return "order/updateOrderForm";
	}

	@RequestMapping("/update_order")
	@LogInvocation
	public String updateOrder(HttpSession session, @RequestParam StatusDto statusDto, Locale locale) {
		OrderDto orderDto = (OrderDto) session.getAttribute("order");
		orderDto.setStatusDto(statusDto);
		service.update(orderDto, locale);
		session.removeAttribute("order");
		return "redirect:all";
	}
	
	@RequestMapping("/checkout")
	@LogInvocation
	public String checkoutOrder(HttpServletRequest req, Model model, Locale locale) {
		HttpSession session = req.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		if (userDto == null) {
			model.addAttribute("message", messageSource.getMessage("general.please.login", null, locale));
			return "user/loginForm";
		}
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		OrderDto orderDto = service.processCart(cart, userDto, locale);
		OrderDto created = service.create(orderDto, locale);
		req.setAttribute("order", created);
		req.setAttribute("message", messageSource.getMessage("order.create.success", null, locale));
		session.removeAttribute("cart");
		return "order/order";
	}
	
	@ExceptionHandler
	public String myAppExc(MyAppException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}

}
