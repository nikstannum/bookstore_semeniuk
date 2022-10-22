//package com.belhard.controller;
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import com.belhard.ContextConfiguration;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//
//@WebListener
//public class ContextListener implements ServletContextListener {
//
//	public static AnnotationConfigApplicationContext context;
//
//	@Override
//	public void contextInitialized(ServletContextEvent sce) {
//		context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent sce) {
//		if (context != null) {
//			context.close();
//		}
//	}
//}
