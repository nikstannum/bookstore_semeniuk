package com.belhard.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.belhard.ContextConfiguration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebListener
public class ContextListener implements ServletContextListener {

	public static AnnotationConfigApplicationContext context;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
		log.info("CONTEXT INIT");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (context != null) {
			context.close();
			log.info("CONTEXT DESTROYED");
		}
	}
}
