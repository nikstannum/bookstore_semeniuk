package com.belhard.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class Logger {

	@AfterReturning("@annotation(LogInvocation)")
	public void loggingInfo(JoinPoint jp) {
		log.info("Method " + jp.getSignature().getName() + " with args " + Arrays.toString(jp.getArgs())
						+ " on the object " + jp.getTarget() + " was called succesfully.");
	}

	@AfterThrowing(pointcut = "@annotation(LogInvocation)", throwing = "e")
	public void loggingError(Exception e) {
		log.error(e);
	}

}
