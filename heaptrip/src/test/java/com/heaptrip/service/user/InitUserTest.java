package com.heaptrip.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.service.user.AuthenticationService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitUserTest extends AbstractTestNGSpringContextTests {

	private static final String IMAGE_NAME = "penguins.jpg";
	
	private List<UserRegistration> users;
	
	private UserRegistration emailUser;
	private UserRegistration socialUser;	
	private UserRegistration fakeUser;
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	private SocialNetwork[] getNet() {
		return new SocialNetwork[] {new SocialNetwork(SocialNetworkEnum.VK, "123", true)};
	}
	
	private SocialNetwork[] getFakeNet()  {
		return new SocialNetwork[] {new SocialNetwork(SocialNetworkEnum.VK, "somebody", true)};
	}
	
	@BeforeTest()
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
		User user;
		users = new ArrayList<UserRegistration>(2);
		
		emailUser = new UserRegistration();
		emailUser.setFirstName("Ivan");
		emailUser.setSecondName("Ivanov");
		emailUser.setPassword("Qwerty2013");
		emailUser.setEmail("ivan@example.com");
		
		user = authenticationService.registration(emailUser, null);
		
		if (user != null) {
			emailUser.setId(user.getId());
			users.add(emailUser);
		}
		
		socialUser = new UserRegistration();
		socialUser.setFirstName("Petr");
		socialUser.setSecondName("Petrov");
		socialUser.setPassword("qwerty2014");
		socialUser.setEmail("petr@example.com");
		socialUser.setNet(getNet());
		
		Resource resource = loader.getResource(IMAGE_NAME);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		
		user = authenticationService.registration(socialUser, is);
		
		if (user != null) {
			socialUser.setId(user.getId());
			users.add(socialUser);
		}
		
		fakeUser = new UserRegistration();
		fakeUser.setFirstName("Somebody");
		fakeUser.setSecondName("Somebody");
		fakeUser.setPassword("nopassword");
		fakeUser.setEmail("somebody@example.com");
		fakeUser.setNet(getFakeNet());
	}
	
	@Test(enabled = true, priority = 1)
	public void confirmUserRegistration() {
		if (users != null && !users.isEmpty()) {
			for (UserRegistration user : users) {
				authenticationService.confirmRegistration(user.getEmail());
			}
		}
	}
	
	@Test(enabled = true, priority = 2)
	public void getUserByEmail() {
		Assert.assertNotNull(authenticationService.getUserByEmail(emailUser.getEmail(), emailUser.getPassword()));
		Assert.assertNotNull(authenticationService.getUserByEmail(socialUser.getEmail(), socialUser.getPassword()));
		Assert.assertNull(authenticationService.getUserByEmail(fakeUser.getEmail(), fakeUser.getPassword()));
	}
	
	@Test(enabled = true, priority = 3)
	public void getUserBySocNetUID() {
		SocialNetwork[] nets = socialUser.getNet();
		Assert.assertNotNull(authenticationService.getUserBySocNetUID(nets[0].getId(), nets[0].getUid()));
		nets = fakeUser.getNet();
		Assert.assertNull(authenticationService.getUserBySocNetUID(nets[0].getId(), nets[0].getUid()));
	}
	
	@AfterTest
	public void afterTest() {
		if (users != null && !users.isEmpty()) {
			for (User user : users) {
				authenticationService.hardRemoveUser(user.getId());
			}
		}
	}
}
