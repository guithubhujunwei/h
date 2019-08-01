package com.itheima.health.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author ：seanyang
 * @date ：Created in 2019/6/9
 * @description ：
 * @version: 1.0
 */
@Slf4j
@Component
@Aspect
public class LogAdvice {

	@Before("execution(* com.itheima.health.controller.*.*(..))")
	public void beforeMethod(JoinPoint joinPoint){

		log.debug("##{}:{}->{}",joinPoint.getTarget().getClass(),joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}
}
