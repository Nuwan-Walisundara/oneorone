package com.dmz.charging.station.finder.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dmz.charging.station.finder.exception.BusinessException;
@Aspect
public class LoggerAOP {
	Logger logger = LoggerFactory.getLogger(LoggerAOP.class);
	
	@AfterThrowing(pointcut="public * *(..)",
			throwing="error")
	  public void doLog( Throwable error) {
		//logger.error("Method Signature: "  + jp.getSignature());  
		logger.error("Exception: "+error);  
	  }

}