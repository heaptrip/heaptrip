package com.heaptrip.service.account.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.service.account.user.UserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitUserTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;

	@BeforeTest()
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
        deleteAccounts();
	}	
	
	@AfterTest
	public void afterTest() {
        deleteAccounts();
	}

    private void deleteAccounts() {
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.NET_USER_ID);
        userService.hardRemove(UserDataProvider.NOT_CONFIRMED_USER_ID);
        userService.hardRemove(UserDataProvider.ACTIVE_USER_ID);
        userService.hardRemove(UserDataProvider.DELETED_USER_ID);
    }
}
