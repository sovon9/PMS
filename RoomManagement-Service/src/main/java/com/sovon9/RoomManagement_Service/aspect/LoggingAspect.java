package com.sovon9.RoomManagement_Service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	// Before advice - runs before the method execution
    @Before("execution(* com.sovon9.RoomManagement_Service.service..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
    	LOGGER.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    // After returning advice - runs after the method returns successfully
    @AfterReturning(pointcut = "execution(* com.sovon9.RoomManagement_Service.service..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
    	LOGGER.info("Method {} returned with value: {}", joinPoint.getSignature().getName(), result);
    }

    // After throwing advice - runs if the method throws an exception
    @AfterThrowing(pointcut = "execution(* com.sovon9.RoomManagement_Service.service..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
    	LOGGER.error("Method {} threw exception: {}", joinPoint.getSignature().toShortString(), error.getMessage());
    }
}
