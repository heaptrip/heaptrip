package com.heaptrip.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.repository.user.OldAuthenticationRepository;
import com.heaptrip.domain.repository.user.OldUserRepository;
import com.heaptrip.domain.service.user.OldAuthenticationService;
import com.heaptrip.domain.service.user.OldUserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private OldUserService userSettingService;
	
	@Autowired
	private OldAuthenticationRepository authenticationRepository;
	
	@Autowired
	private OldUserRepository userSettingRepository;

	@Autowired
	private OldAuthenticationService authenticationService;
	
	@Test(enabled = true, priority = 1, expectedExceptions = IllegalArgumentException.class)
	public void saveSettingFakeUser() {
		UserSetting setting = new UserSetting();
		setting.setAdsFromClub(true);
		setting.setAdsFromAgency(false);
		setting.setAdsFromCompany(false);
		userSettingService.saveSetting(InitUserTest.FAKE_USER_ID, setting);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = IllegalArgumentException.class)
	public void saveSettingNotConfirmedUser() {
		UserSetting setting = new UserSetting();
		setting.setAdsFromClub(true);
		setting.setAdsFromAgency(false);
		setting.setAdsFromCompany(false);
		userSettingService.saveSetting(InitUserTest.NOTCONFIRMED_USER_ID, setting);
	}
	
	@Test(enabled = true, priority = 3)
	public void saveSetting() {
		User user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getSetting());
		((UserSetting) user.getSetting()).setAdsFromClub(true);
		((UserSetting) user.getSetting()).setAdsFromAgency(false);
		((UserSetting) user.getSetting()).setAdsFromCompany(false);
		
		userSettingService.saveSetting(InitUserTest.NET_USER_ID, (UserSetting) user.getSetting());
		user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getSetting());
		Assert.assertFalse(((UserSetting) user.getSetting()).getAdsFromAgency());
		Assert.assertTrue(((UserSetting) user.getSetting()).getAdsFromClub());
		Assert.assertFalse(((UserSetting) user.getSetting()).getAdsFromCompany());
	}	
	
	@Test(enabled = true, priority = 5, expectedExceptions = IllegalArgumentException.class)
	public void profileImageFromFakeUser() {
		userSettingService.profileImageFrom(InitUserTest.FAKE_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 6, expectedExceptions = IllegalArgumentException.class)
	public void profileImageFromNotConfirmedUser() {
		userSettingService.profileImageFrom(InitUserTest.NOTCONFIRMED_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 7, expectedExceptions = IllegalArgumentException.class)
	public void profileImageFromEmailUser() {
		userSettingService.profileImageFrom(InitUserTest.EMAIL_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 8)
	public void profileImageFrom() {
		userSettingService.profileImageFrom(InitUserTest.NET_USER_ID, SocialNetworkEnum.NONE);
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertFalse(user.getExternalImageStore().equals(SocialNetworkEnum.NONE));
	}
	
	@Test(enabled = true, priority = 9, expectedExceptions = IllegalArgumentException.class)
	public void unlinkSocialNetworkFakeUser() {
		userSettingService.unlinkSocialNetwork(InitUserTest.FAKE_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 10, expectedExceptions = IllegalArgumentException.class)
	public void unlinkSocialNetworkNotConfirmedUser() {
		userSettingService.unlinkSocialNetwork(InitUserTest.NOTCONFIRMED_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = IllegalArgumentException.class)
	public void unlinkSocialNetworkEmailUser() {
		userSettingService.unlinkSocialNetwork(InitUserTest.EMAIL_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = IllegalArgumentException.class)
	public void unlinkSocialNetworkWrongSocialNetwork() {
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = IllegalArgumentException.class)
	public void unlinkSocialNetworkEmptyPassword() {
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 14)
	public void unlinkSocialNetworkOk() {
		authenticationService.changePassword(InitUserTest.NET_USER_ID, "", InitUserTest.NET_USER_PSWD);
		userSettingService.unlinkSocialNetwork(InitUserTest.NET_USER_ID, SocialNetworkEnum.VK);
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		if (user.getNet() != null) {
			SocialNetwork[] nets = user.getNet();
			
			for (SocialNetwork net : nets) {
				Assert.assertTrue(!net.getId().equals(SocialNetworkEnum.VK.toString()));
			}
		}
	}
	
	@Test(enabled = true, priority = 16, expectedExceptions = IllegalArgumentException.class)
	public void linkSocialNetworkFakeUser() {
		userSettingService.linkSocialNetwork(InitUserTest.FAKE_USER_ID, InitUserTest.getFB());
	}
	
	@Test(enabled = true, priority = 17, expectedExceptions = IllegalArgumentException.class)
	public void linkSocialNotConfirmedUser() {
		userSettingService.linkSocialNetwork(InitUserTest.NOTCONFIRMED_USER_ID, InitUserTest.getFB());
	}
	
	@Test(enabled = true, priority = 18)
	public void linkSocialNetwork() {
		userSettingService.linkSocialNetwork(InitUserTest.NET_USER_ID, InitUserTest.getFB());
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		if (user.getNet() != null) {
			SocialNetwork[] nets = user.getNet();
			
			for (SocialNetwork net : nets) {
				Assert.assertTrue(net.getId().equals(SocialNetworkEnum.FB.toString()));
			}
		}
	}
	
	@Test(enabled = true, priority = 19, expectedExceptions = IllegalArgumentException.class)
	public void linkSocialNetworkExists() {
		userSettingService.linkSocialNetwork(InitUserTest.NET_USER_ID, InitUserTest.getFB());
	}
	
	@Test(enabled = true, priority = 70, expectedExceptions = IllegalArgumentException.class)
	public void saveProfileFakeUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userSettingService.saveProfile(InitUserTest.FAKE_USER_ID, profile);
	}
	
	@Test(enabled = true, priority = 71, expectedExceptions = IllegalArgumentException.class)
	public void saveProfileNotConfirmedUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userSettingService.saveProfile(InitUserTest.NOTCONFIRMED_USER_ID, profile);
	}
	
	@Test(enabled = true, priority = 72)
	public void saveProfile() {
		User user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);
		((UserProfile) user.getProfile()).setDesc("description");
		
		userSettingService.saveProfile(InitUserTest.NET_USER_ID, (UserProfile) user.getProfile());
		user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getProfile());
		Assert.assertNotNull(user.getProfile().getDesc());
	}
	
	@Test(enabled = true, priority = 73)
	public void updateProfile() {
		User user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);
		((UserProfile) user.getProfile()).setBirthday(new Date());
		
		userSettingService.saveProfile(InitUserTest.NET_USER_ID, (UserProfile) user.getProfile());
		user = userSettingRepository.findOne(InitUserTest.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getProfile());
		Assert.assertNotNull(user.getProfile().getDesc());
		Assert.assertNotNull(((UserProfile) user.getProfile()).getBirthday());
	}
	
	@Test(enabled = true, priority = 90, expectedExceptions = IllegalArgumentException.class)
	public void deleteUserFakeUser() {
		userSettingService.deleteUser(InitUserTest.FAKE_USER_ID);
	}
	
	@Test(enabled = true, priority = 91, expectedExceptions = IllegalArgumentException.class)
	public void deleteUserNotConfirmedUser() {
		userSettingService.deleteUser(InitUserTest.NOTCONFIRMED_USER_ID);
	}
	
	@Test(enabled = true, priority = 92)
	public void deleteUser() {
		userSettingService.deleteUser(InitUserTest.NET_USER_ID);
		User user = authenticationRepository.findOne(InitUserTest.NET_USER_ID);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
