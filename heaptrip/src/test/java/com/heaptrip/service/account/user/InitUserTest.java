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

	public static final String IMAGE_1 = "penguins.jpg";
	public static final String IMAGE_2 = "tulips.jpg";
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private UserService userService;
	
	public static String EMAIL_USER_ID = "email";
	public static String EMAIL_USER_EMAIL = "support@heaptrip.com";
	public static String EMAIL_USER_EMAIL_NEW = "noreply@heaptrip.com";
	public static String EMAIL_USER_PSWD = "Qwerty2013";
	public static String EMAIL_USER_PSWD_NEW = "new_Qwerty2013";
	
	public static String NET_USER_ID = "net";
	public static String NET_USER_EMAIL = "support@heaptrip.com";
	public static String NET_USER_PSWD = "qwerty2014";
	
	public static String NOTCONFIRMED_USER_ID = "notConfirmed";
	public static String NOTCONFIRMED_USER_EMAIL = "notConfirmed@example.com";
	public static String NOTCONFIRMED_USER_PSWD = "notConfirmed";
	
	public static String FAKE_USER_ID = "fake";
	public static String FAKE_USER_EMAIL = "somebody@example.com";
	public static String FAKE_USER_PSWD = "nopassword";
	
	public static String INCORRECT_EMAIL = "!@#$%";
	
	public static SocialNetwork[] getNets() {
		return new SocialNetwork[]{getVK()};
	}
	
	public static SocialNetwork getVK() {
		return new SocialNetwork(SocialNetworkEnum.VK, "123");
	}
	
	public static SocialNetwork getFB() {
		return new SocialNetwork(SocialNetworkEnum.FB, "345");
	}
	
	public static SocialNetwork getFakeNet() {
		return new SocialNetwork(SocialNetworkEnum.VK, "fake");
	}
	
	@BeforeTest()
	public void init() throws Exception {
		
		this.springTestContextPrepareTestInstance();
		Locale locale = new Locale("ru");
		
		UserRegistration emailUser = new UserRegistration();
		emailUser.setId(EMAIL_USER_ID);
		emailUser.setName("Ivan Ivanov");
		emailUser.setPassword(EMAIL_USER_PSWD);
		emailUser.setEmail(EMAIL_USER_EMAIL);
		
		userService.registration(emailUser, null, locale);
		
		UserRegistration notConfirmedUser = new UserRegistration();
		notConfirmedUser.setId(NOTCONFIRMED_USER_ID);
		notConfirmedUser.setName("Igor Igorev");
		notConfirmedUser.setPassword(NOTCONFIRMED_USER_PSWD);
		notConfirmedUser.setEmail(NOTCONFIRMED_USER_EMAIL);
		
		userService.registration(notConfirmedUser, null, locale);
				
		UserRegistration netUser = new UserRegistration();
		netUser.setId(NET_USER_ID);
		netUser.setName("Petr Petrov");
		netUser.setEmail(NET_USER_EMAIL);
		netUser.setNet(getNets());
		
		Resource resource = loader.getResource(IMAGE_1);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		
		userService.registration(netUser, is, locale);
	}	
	
	@AfterTest
	public void afterTest() {
		userService.hardRemove(NOTCONFIRMED_USER_ID);
		userService.hardRemove(EMAIL_USER_ID);
		userService.hardRemove(NET_USER_ID);
	}
}
