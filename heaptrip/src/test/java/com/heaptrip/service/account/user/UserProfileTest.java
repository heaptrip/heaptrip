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
public class UserProfileTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
	public void saveProfileFakeUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userService.saveProfile(UserDataProvider.FAKE_USER_ID, profile);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
	public void saveProfileNotConfirmedUser() {
		UserProfile profile = new UserProfile();
		profile.setDesc("description");
		
		userService.saveProfile(UserDataProvider.NOT_CONFIRMED_USER_ID, profile);
	}

    @Test(enabled = true, priority = 3, expectedExceptions = AccountException.class)
    public void saveProfileDeletedUser() {
        UserProfile profile = new UserProfile();
        profile.setDesc("description");

        userService.saveProfile(UserDataProvider.DELETED_USER_ID, profile);
    }
	
	@Test(enabled = true, priority = 4)
	public void saveProfile() {
		User user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);
		((UserProfile) user.getProfile()).setDesc("description");
		
		userService.saveProfile(UserDataProvider.NET_USER_ID, (UserProfile) user.getProfile());
		user = (User) userRepository.findOne(UserDataProvider.NET_USER_ID);

		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getProfile());
		Assert.assertNotNull(user.getProfile().getDesc());
	}
	
	@Test(enabled = true, priority = 5)
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
}
