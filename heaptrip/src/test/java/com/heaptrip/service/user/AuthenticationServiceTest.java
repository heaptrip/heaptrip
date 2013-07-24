package com.heaptrip.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.service.user.AuthenticationService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AuthenticationServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;

	@Test(enabled = true, priority = 1)
	public void confirmRegistration() {
		
		Assert.assertTrue(authenticationService.confirmRegistration(InitUserTest.EMAIL_USER_EMAIL));
		Assert.assertTrue(authenticationService.confirmRegistration(InitUserTest.NET_USER_EMAIL));
		Assert.assertFalse(authenticationService.confirmRegistration(InitUserTest.FAKE_USER_EMAIL));
	}
	
	@Test(enabled = true, priority = 2)
	public void getUserByEmail() {
		
		User user;
		
		user = authenticationService.getUserByEmail(InitUserTest.EMAIL_USER_EMAIL, InitUserTest.EMAIL_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.ACTIVE));
		
		user = authenticationService.getUserByEmail(InitUserTest.NET_USER_EMAIL, InitUserTest.NET_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.ACTIVE));
		
		user = authenticationService.getUserByEmail(InitUserTest.FAKE_USER_EMAIL, InitUserTest.FAKE_USER_PSWD);
		Assert.assertNull(user);
	}
	
	@Test(enabled = true, priority = 3)
	public void getUserBySocNetUID() throws IOException, NoSuchAlgorithmException {
		
		User user;
		SocialNetwork[] net;
		Byte[] imageCRC;
		
		Resource resource;
		File file;
		InputStream is;
		
		// load image 1
		resource = loader.getResource(InitUserTest.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// fake user
		net = InitUserTest.getFakeNet();
		Assert.assertNull(authenticationService.getUserBySocNetUID(net[0].getId(), net[0].getUid(), is));
		
		// load image 1
		resource = loader.getResource(InitUserTest.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user
		net = InitUserTest.getNet();
		user = authenticationService.getUserBySocNetUID(net[0].getId(), net[0].getUid(), is);
		Assert.assertNotNull(user);
		imageCRC = user.getImageCRC();
		
		// load image 2
		resource = loader.getResource(InitUserTest.IMAGE_2);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user with new image
		user = authenticationService.getUserBySocNetUID(net[0].getId(), net[0].getUid(), is);
		Assert.assertFalse(user.getImageCRC().equals(imageCRC));
	}
	
	@Test(enabled = true, priority = 4)
	public void resetPassword() throws MessagingException {
		
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(InitUserTest.EMAIL_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 4)
	public void sendNewPassword() throws MessagingException {
		
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(InitUserTest.EMAIL_USER_EMAIL, String.valueOf(InitUserTest.EMAIL_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 6)
	public void changePassword() {
		
		Assert.assertTrue(authenticationService.changePassword(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_PSWD, 
																InitUserTest.EMAIL_USER_PSWD_NEW));
		
		Assert.assertFalse(authenticationService.changePassword(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_PSWD, 
																InitUserTest.EMAIL_USER_PSWD_NEW));
	}
	
	@Test(enabled = true, priority = 7)
	public void changeEmail() {
		Assert.assertTrue(authenticationService.changeEmail(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_EMAIL, 
																InitUserTest.EMAIL_USER_EMAIL_NEW));
		
		Assert.assertFalse(authenticationService.changeEmail(InitUserTest.EMAIL_USER_ID, 
															InitUserTest.EMAIL_USER_EMAIL, 
															InitUserTest.EMAIL_USER_EMAIL_NEW));
	}
}
