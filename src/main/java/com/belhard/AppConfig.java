package com.belhard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.belhard.controller.MyLocaleResolver;
import com.belhard.controller.filter.AuthorizationFilter;
import com.belhard.interceptor.PrintLogInterceptor;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories
@ServletComponentScan
public class AppConfig implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
		FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>();
		AuthorizationFilter authorizationFilter = new AuthorizationFilter(messageSource());
		registration.setFilter(authorizationFilter);
		registration.addUrlPatterns("/users/*", "/orders/*");
		registration.setOrder(1);
		return registration;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		PrintLogInterceptor printLogInterceptor = new PrintLogInterceptor();
		InterceptorRegistration registration = registry.addInterceptor(printLogInterceptor);
		registration.addPathPatterns("/**");
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new MyLocaleResolver();
		localeResolver.setCookieMaxAge(120);
		return localeResolver;
	}
	

}
