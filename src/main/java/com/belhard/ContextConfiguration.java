package com.belhard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.belhard.controller.filter.AuthorizationFilter;
import com.belhard.interceptor.PrintLogInterceptor;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories
public class ContextConfiguration implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(ContextConfiguration.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
		FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new AuthorizationFilter());
		registration.addUrlPatterns("/users/*", "/orders/*");
		registration.setOrder(1);
		return registration;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		PrintLogInterceptor printLogInterceptor = new PrintLogInterceptor();
		InterceptorRegistration registration = registry.addInterceptor(printLogInterceptor);
		registration.addPathPatterns("/**");
	}
}
