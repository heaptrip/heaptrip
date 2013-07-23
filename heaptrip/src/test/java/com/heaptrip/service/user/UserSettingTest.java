package com.heaptrip.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.exception.BusinessExeption;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.domain.repository.user.UserSettingRepository;
import com.heaptrip.domain.service.user.AuthenticationService;
import com.heaptrip.domain.service.user.UserSettingService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserSettingTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserSettingService userSettingService;
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private UserSettingRepository userSettingRepository;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Test(enabled = true, priority = 1)
	public void saveSetting() {
		UserSetting user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);
		user.setAdsFromClub(true);
		userSettingService.saveSetting(user);
		user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertFalse(user.getAdsFromAgency());
		Assert.assertTrue(user.getAdsFromClub());
		Assert.assertFalse(user.getAdsFromCompany());
	}
	
	@Test(enabled = true, priority = 2)
	public void profileImageFrom() {
		userSettingService.profileImageFrom(InitUserTest.NET_USER_ID, SocialNetworkEnum.NONE.toString());
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertTrue(user.getExternalImageStore().equals(SocialNetworkEnum.NONE));
	}
	
	@Test(enabled = true, priority = 3, expectedExceptions = BusinessExeption.class)
	public void unlinkSocialNetwork() {
		// exception, not find social network
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.FB.toString());
		// exception, password is empty
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.VK.toString());
		// ok
		authenticationService.changePassword(InitUserTest.NET_USER_ID, InitUserTest.NET_USER_PSWD, "newpassword");
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.VK.toString());
	}
	
	@Test(enabled = true, priority = 4)
	public void deleteUserProfile() {
		userSettingService.deleteUserProfile(InitUserTest.NET_USER_ID);
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
