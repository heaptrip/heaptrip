package com.heaptrip.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.repository.MongoContextImpl;

@Aspect
@Service
public class ErrorHandlerAspect {

	private static final Logger logger = LoggerFactory.getLogger(MongoContextImpl.class);

	@Autowired
	private CategoryService categoryService;

	@Pointcut("execution(* com.heaptrip.service..*(..))")
	public void inServiceLayer() {
	}

	@AfterThrowing(pointcut = "inServiceLayer()", throwing = "e")
	public void doBeforeTask(JoinPoint joinPoint, Throwable e) {
		logger.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()");
	}
}