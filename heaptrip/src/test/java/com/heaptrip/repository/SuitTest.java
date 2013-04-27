package com.heaptrip.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.heaptrip.domain.repository.CategoryRepository;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class SuitTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void f() {
		// categoryRepository.findAll();
	}

	@BeforeSuite()
	public void beforeSuite() throws Exception {
		this.springTestContextPrepareTestInstance();
		categoryRepository.findAll();
		System.out.println("beforeSuite");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("afterSuite");
	}

}
