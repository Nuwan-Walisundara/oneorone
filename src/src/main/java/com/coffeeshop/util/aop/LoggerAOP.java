package com.coffeeshop.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
@Aspect
@Configuration
public class LoggerAOP {
	Logger logger = LoggerFactory.getLogger(LoggerAOP.class);
	
	@AfterThrowing(value="execution(* com.coffeeshop.service.OrderImpl.*(..))",throwing="error")
	  public void doLog(JoinPoint joinPoint, Throwable error) {
		logger.error("Method Signature: "  + joinPoint.getSignature());  
		logger.error("Exception: "+error);  
	  }

	@Before("execution(* com.coffeeshop.service.*.*(..)))")
	// @Pointcut("execution(* com.dmz.charging.station.*.*(..))")
	private void anyOldTransfer(JoinPoint joinPoint) {
		logger.debug("Method Signature: "  + joinPoint.getSignature()); 
		logger.debug(StringUtils.arrayToCommaDelimitedString(joinPoint.getArgs()) );
	}// the pointcut signature
	
}
