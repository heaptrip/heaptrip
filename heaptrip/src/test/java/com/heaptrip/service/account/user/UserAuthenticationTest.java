package com.heaptrip.service.account.user;

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
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.account.user.AuthenticationService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserAuthenticationTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Test(enabled = true, priority = 1)
	public void getUserByEmail() {
		User user;
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.EMAIL_USER_EMAIL, UserDataProvider.EMAIL_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.ACTIVE));
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.NOT_CONFIRMED_USER_EMAIL, UserDataProvider.NOT_CONFIRMED_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.NOTCONFIRMED));

        user = authenticationService.getUserByEmailAndPassword(UserDataProvider.DELETED_USER_EMAIL, UserDataProvider.DELETED_USER_PSWD);
        Assert.assertNotNull(user);
        Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.DELETED));
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.NET_USER_EMAIL, UserDataProvider.NET_USER_PSWD);
		Assert.assertNull(user);
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.FAKE_USER_EMAIL, UserDataProvider.FAKE_USER_PSWD);
		Assert.assertNull(user);
	}
	
	@Test(enabled = true, priority = 11)
	public void getUserBySocNetUID() throws IOException, NoSuchAlgorithmException {
		
		User user;
		SocialNetwork net;
		Byte[] imageCRC;
		
		Resource resource;
		File file;
		InputStream is;
		
		// load image 1
		resource = loader.getResource(UserDataProvider.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// fake user
		net = UserDataProvider.getFakeNet();
		Assert.assertNull(authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is));
		
		// load image 1
		resource = loader.getResource(UserDataProvider.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user
		net = UserDataProvider.getVK();
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertNotNull(user);
		imageCRC = user.getImageCRC();
		
		// load image 2
		resource = loader.getResource(UserDataProvider.IMAGE_2);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user with new image
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertFalse(user.getImageCRC().equals(imageCRC));
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = {AccountException.class, MessagingException.class})
	public void resetPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.FAKE_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = {AccountException.class, MessagingException.class})
	public void resetPasswordNotConfirmedUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.NOT_CONFIRMED_USER_EMAIL, locale);
	}

    @Test(enabled = true, priority = 13, expectedExceptions = {AccountException.class, MessagingException.class})
    public void resetPasswordDeletedUser() throws MessagingException {
        Locale locale = new Locale("ru");
        authenticationService.resetPassword(UserDataProvider.DELETED_USER_EMAIL, locale);
    }
	
	@Test(enabled = true, priority = 14)
	public void resetPasswordEmailUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.EMAIL_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 21, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.FAKE_USER_ID, String.valueOf(UserDataProvider.FAKE_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 22, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordNotConfirmedUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.NOT_CONFIRMED_USER_ID, String.valueOf(UserDataProvider.NOT_CONFIRMED_USER_ID.hashCode()), locale);
	}

    @Test(enabled = true, priority = 23, expectedExceptions = {AccountException.class, MessagingException.class})
    public void sendNewPasswordDeletedUser() throws MessagingException {
        Locale locale = new Locale("ru");
        authenticationService.sendNewPassword(UserDataProvider.DELETED_USER_ID, String.valueOf(UserDataProvider.DELETED_USER_ID.hashCode()), locale);
    }
	
	@Test(enabled = true, priority = 24, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordWrongValue() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.EMAIL_USER_ID, "1234567890", locale);
	}
	
	@Test(enabled = true, priority = 25)
	public void sendNewPassword() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()), locale);
	}
}
