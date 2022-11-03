package com.belhard.controller.command.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belhard.aop.LogInvocation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class RestCartCommand {

	@PostMapping()
	@LogInvocation
	public void addToCart(HttpServletRequest req, @RequestBody String str) {
		String rawId = str.split("=")[1];
		Long bookId = Long.parseLong(rawId);
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
	}

}
