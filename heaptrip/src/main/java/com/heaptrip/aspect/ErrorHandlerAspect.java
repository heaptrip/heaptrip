package com.heaptrip.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.service.category.CategoryService;

@Aspect
@Service
public class ErrorHandlerAspect {

	@Autowired
	private CategoryService categoryService;

	@Pointcut("execution(* com.heaptrip.domain.service..*(..))")
	public void inServiceLayer() {
	}

	@AfterThrowing("inServiceLayer()")
	public void doBeforeTask(JoinPoint joinPoint) {
		System.out.println("hi : " + joinPoint.getSignature().getName());
	}
}