package com.belhard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PrintLogInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
		System.out.printf("Interceptor-PRE: request: %s, method: %s%n", request.getRequestURI(), request.getMethod());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
		System.out.printf("Interceptor-POST: request: %s, method: %s%n", request.getServletPath(), request.getMethod());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		System.out.printf("Interceptor-AFTER: request: %s, method: %s%n", request.getServletPath(),
						request.getMethod());
	}
}
