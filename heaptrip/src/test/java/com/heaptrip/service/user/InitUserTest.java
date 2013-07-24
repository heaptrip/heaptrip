package com.heaptrip.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.service.user.AuthenticationService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitUserTest extends AbstractTestNGSpringContextTests {

	public static final String IMAGE_1 = "penguins.jpg";
	public static final String IMAGE_2 = "tulips.jpg";
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	public static String EMAIL_USER_ID = "email";
	public static String EMAIL_USER_EMAIL = "ivan@example.com";
	public static String EMAIL_USER_EMAIL_NEW = "new_ivan@example.com";
	public static String EMAIL_USER_PSWD = "Qwerty2013";
	public static String EMAIL_USER_PSWD_NEW = "new_Qwerty2013";
	
	public static String NET_USER_ID = "net";
	public static String NET_USER_EMAIL = "petr@example.com";
	public static String NET_USER_PSWD = "qwerty2014";
	
	public static String FAKE_USER_ID = "fake";
	public static String FAKE_USER_EMAIL = "somebody@example.com";
	public static String FAKE_USER_PSWD = "nopassword";
	
	public static SocialNetwork[] getNet() {
		return new SocialNetwork[] {new SocialNetwork(SocialNetworkEnum.VK, "123")};
	}
	
	public static SocialNetwork[] getFakeNet()  {
		return new SocialNetwork[] {new SocialNetwork(SocialNetworkEnum.VK, "somebody")};
	}
	
	@BeforeTest()
	public void init() throws Exception {
		
		this.springTestContextPrepareTestInstance();
		
		UserRegistration emailUser = new UserRegistration();
		emailUser.setId(EMAIL_USER_ID);
		emailUser.setName("Ivan Ivanov");
		emailUser.setPassword(EMAIL_USER_PSWD);
		emailUser.setEmail(EMAIL_USER_EMAIL);
		
		authenticationService.registration(emailUser, null);
		
		UserRegistration netUser = new UserRegistration();
		netUser.setId(NET_USER_ID);
		netUser.setName("Petr Petrov");
		netUser.setPassword(NET_USER_PSWD);
		netUser.setEmail(NET_USER_EMAIL);
		netUser.setNet(getNet());
		
		Resource resource = loader.getResource(IMAGE_1);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		
		authenticationService.registration(netUser, is);
	}	
	
	@AfterTest
	public void afterTest() {
		
		authenticationService.hardRemoveUser(EMAIL_USER_ID);
		authenticationService.hardRemoveUser(NET_USER_ID);
	}
}
