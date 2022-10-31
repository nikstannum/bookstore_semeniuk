package com.belhard.controller.command.impl.orders;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belhard.aop.LogInvocation;
import com.belhard.service.OrderService;
import com.belhard.service.dto.OrderDto;
import com.belhard.service.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartCommand {
	private final OrderService orderService;
	private final MessageSource messageSource;

	@RequestMapping("add_to_cart")
	@LogInvocation
	public String addToCart(HttpServletRequest req) {
		Long bookId = Long.parseLong(req.getParameter("bookId"));
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		Integer quantuty = cart.get(bookId);
		if (quantuty == null) {
			cart.put(bookId, 1);
		} else {
			cart.put(bookId, quantuty + 1);
		}
		session.setAttribute("cart", cart);
		String currentCommand = req.getParameter("currentCommand");
		String currentPage = req.getParameter("currentPage");
		return "redirect:" + currentCommand + "?page=" + currentPage;
	}

	@RequestMapping("orders/cart")
	@LogInvocation
	public String cartCommand(HttpSession session, Model model, Locale locale) {
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		UserDto user = (UserDto) session.getAttribute("user");
		if (cart == null) {
			model.addAttribute("message", messageSource.getMessage("cart.empty", null, locale));
			return "order/cart";
		}
		model.addAttribute("message", messageSource.getMessage("cart.products", null, locale));
		OrderDto processed = orderService.processCart(cart, user, locale);
		model.addAttribute("cart", processed);
		return "order/cart";
	}

}
