package com.heaptrip.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.heaptrip.domain.repository.CategoryRepository;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class GroupTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void f() {

	}

	@BeforeTest()
	public void beforeTest() throws Exception {
		this.springTestContextPrepareTestInstance();
		System.out.println("beforeTest");
		categoryRepository.findAll();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("afterTest");
	}

}
