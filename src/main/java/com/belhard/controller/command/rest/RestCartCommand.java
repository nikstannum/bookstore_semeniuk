package com.belhard.controller.command.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belhard.aop.LogInvocation;

@RestController
@RequestMapping("api/cart")
public class RestCartCommand {

	@PostMapping("/{id}")
	@LogInvocation
	public void addToCart(HttpSession session, @PathVariable Long id) {
		@SuppressWarnings("unchecked")
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		Integer quantuty = cart.get(id);
		if (quantuty == null) {
			cart.put(id, 1);
		} else {
			cart.put(id, quantuty + 1);
		}
		session.setAttribute("cart", cart);
	}

}
