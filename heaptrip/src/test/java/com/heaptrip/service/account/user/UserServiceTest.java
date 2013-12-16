package com.heaptrip.service.account.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserProfile;
import com.heaptrip.domain.entity.account.user.UserSetting;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.UserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
	public void saveSettingFakeUser() {
		UserSetting setting = new UserSetting();
		setting.setAdsFromClub(true);
		setting.setAdsFromAgency(false);
		setting.setAdsFromCompany(false);
		userService.saveSetting(UserDataProvider.FAKE_USER_ID, setting);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
	public void saveSettingNotConfirmedUser() {
		UserSetting setting = new UserSetting();
		setting.setAdsFromClub(true);
		setting.setAdsFromAgency(false);
		setting.setAdsFromCompany(false);
		userService.saveSetting(UserDataProvider.NOT_CONFIRMED_USER_ID, setting);
	}
	
	@Test(enabled = true, priority = 3)
	public void saveSetting() {
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getSetting());
		((UserSetting) user.getSetting()).setAdsFromClub(true);
		((UserSetting) user.getSetting()).setAdsFromAgency(false);
		((UserSetting) user.getSetting()).setAdsFromCompany(false);
		
		userService.saveSetting(UserDataProvider.NET_USER_ID, (UserSetting) user.getSetting());
		user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getSetting());
		Assert.assertFalse(((UserSetting) user.getSetting()).getAdsFromAgency());
		Assert.assertTrue(((UserSetting) user.getSetting()).getAdsFromClub());
		Assert.assertFalse(((UserSetting) user.getSetting()).getAdsFromCompany());
	}	
	
	@Test(enabled = true, priority = 5, expectedExceptions = AccountException.class)
	public void profileImageFromFakeUser() {
		userService.profileImageFrom(UserDataProvider.FAKE_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 6, expectedExceptions = AccountException.class)
	public void profileImageFromNotConfirmedUser() {
		userService.profileImageFrom(UserDataProvider.NOT_CONFIRMED_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 7, expectedExceptions = AccountException.class)
	public void profileImageFromEmailUser() {
		userService.profileImageFrom(UserDataProvider.EMAIL_USER_ID, SocialNetworkEnum.VK);
	}
	
	@Test(enabled = true, priority = 8)
	public void profileImageFrom() {
		userService.profileImageFrom(UserDataProvider.NET_USER_ID, SocialNetworkEnum.NONE);
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		Assert.assertFalse(user.getExternalImageStore().equals(SocialNetworkEnum.NONE));
	}
	
	@Test(enabled = true, priority = 9, expectedExceptions = AccountException.class)
	public void unlinkSocialNetworkFakeUser() {
		userService.unlinkSocialNetwork(UserDataProvider.FAKE_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 10, expectedExceptions = AccountException.class)
	public void unlinkSocialNetworkNotConfirmedUser() {
		userService.unlinkSocialNetwork(UserDataProvider.NOT_CONFIRMED_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = AccountException.class)
	public void unlinkSocialNetworkEmailUser() {
		userService.unlinkSocialNetwork(UserDataProvider.EMAIL_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = AccountException.class)
	public void unlinkSocialNetworkWrongSocialNetwork() {
		userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = AccountException.class)
	public void unlinkSocialNetworkEmptyPassword() {
		userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.FB);
	}
	
	@Test(enabled = true, priority = 14)
	public void unlinkSocialNetworkOk() {
		userService.changePassword(UserDataProvider.NET_USER_ID, "", UserDataProvider.NET_USER_PSWD);
		userService.unlinkSocialNetwork(UserDataProvider.NET_USER_ID, SocialNetworkEnum.VK);
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		if (user.getNet() != null) {
			SocialNetwork[] nets = user.getNet();
			
			for (SocialNetwork net : nets) {
				Assert.assertTrue(!net.getId().equals(SocialNetworkEnum.VK.toString()));
			}
		}
	}
	
	@Test(enabled = true, priority = 16, expectedExceptions = AccountException.class)
	public void linkSocialNetworkFakeUser() {
		userService.linkSocialNetwork(UserDataProvider.FAKE_USER_ID, UserDataProvider.getFB());
	}
	
	@Test(enabled = true, priority = 17, expectedExceptions = AccountException.class)
	public void linkSocialNotConfirmedUser() {
		userService.linkSocialNetwork(UserDataProvider.NOT_CONFIRMED_USER_ID, UserDataProvider.getFB());
	}
	
	@Test(enabled = true, priority = 18)
	public void linkSocialNetwork() {
		userService.linkSocialNetwork(UserDataProvider.NET_USER_ID, UserDataProvider.getFB());
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		if (user.getNet() != null) {
			SocialNetwork[] nets = user.getNet();
			
			for (SocialNetwork net : nets) {
				Assert.assertTrue(net.getId().equals(SocialNetworkEnum.FB.toString()));
			}
		}
	}
	
	@Test(enabled = true, priority = 19, expectedExceptions = AccountException.class)
	public void linkSocialNetworkExists() {
		userService.linkSocialNetwork(UserDataProvider.NET_USER_ID, UserDataProvider.getFB());
	}
	
	@Test(enabled = true, priority = 70, expectedExceptions = AccountException.class)
	public void saveProfileFakeUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userService.saveProfile(UserDataProvider.FAKE_USER_ID, profile);
	}
	
	@Test(enabled = true, priority = 71, expectedExceptions = AccountException.class)
	public void saveProfileNotConfirmedUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userService.saveProfile(UserDataProvider.NOT_CONFIRMED_USER_ID, profile);
	}
	
	@Test(enabled = true, priority = 72)
	public void saveProfile() {
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		((UserProfile) user.getProfile()).setDesc("description");
		
		userService.saveProfile(UserDataProvider.NET_USER_ID, (UserProfile) user.getProfile());
		user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getProfile());
		Assert.assertNotNull(user.getProfile().getDesc());
	}
	
	@Test(enabled = true, priority = 73)
	public void updateProfile() {
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		((UserProfile) user.getProfile()).setBirthday(new Date());
		
		userService.saveProfile(UserDataProvider.NET_USER_ID, (UserProfile) user.getProfile());
		user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getProfile());
		Assert.assertNotNull(user.getProfile().getDesc());
		Assert.assertNotNull(((UserProfile) user.getProfile()).getBirthday());
	}
	
	@Test(enabled = true, priority = 90, expectedExceptions = AccountException.class)
	public void deleteUserFakeUser() {
		userService.delete(UserDataProvider.FAKE_USER_ID);
	}
	
	@Test(enabled = true, priority = 91, expectedExceptions = AccountException.class)
	public void deleteUserNotConfirmedUser() {
		userService.delete(UserDataProvider.NOT_CONFIRMED_USER_ID);
	}
	
	@Test(enabled = true, priority = 92)
	public void deleteUser() {
		userService.delete(UserDataProvider.NET_USER_ID);
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
