package com.webcustomer_tracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// setup pointcut declaration
	@Pointcut("execution (* com.webcustomer_tracker.controller.*.*(..))")
	public void forControllerPackage() {
	}

	// do the same for service and dao
	@Pointcut("execution (* com.webcustomer_tracker.service.*.*(..))")
	public void forServicePackage() {
	}

	@Pointcut("execution (* com.webcustomer_tracker.dao.*.*(..))")
	public void forDaoPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	public void forAppFlow() {
	}

	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint jp) {

		// display method we are calling
		String method = jp.getSignature().toShortString();
		logger.info("==================>>>>>> in @Before: calling method: " + method);

		// display the arguments to the method

		// get the arguments
		Object[] args = jp.getArgs();

		// loop thru and display args
		for (Object arg : args) {
			logger.info("============ >>>> argument: " + arg);
		}
	}

	// add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint jp, Object result) {

		// display method we are returning from
		String method = jp.getSignature().toShortString();
		logger.info("==================>>>>>> in @AfterReturning: from method: " + method);

		// display data returned
		logger.info("==============>>>> result: " + result);

	}

}
