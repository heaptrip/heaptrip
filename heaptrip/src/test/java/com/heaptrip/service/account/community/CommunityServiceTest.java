package com.heaptrip.service.account.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.account.community.CommunityProfile;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.domain.service.account.community.CommunityService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CommunityServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Test(enabled = true, priority = 1)
	public void confirmRegistrationCommunity() {
		communityService.confirmRegistration(InitCommunityTest.COMMUNITY_ID, String.valueOf(InitCommunityTest.COMMUNITY_ID.hashCode()));
	}

	@Test(enabled = true, priority = 2, expectedExceptions = IllegalArgumentException.class)
	public void confirmRegistrationFakeCommunity() {
		communityService.confirmRegistration(InitCommunityTest.FAKE_COMMUNITY_ID, String.valueOf(InitCommunityTest.FAKE_COMMUNITY_ID.hashCode()));
	}	
	
	@Test(enabled = true, priority = 11, expectedExceptions = IllegalArgumentException.class)
	public void saveSettingFakeCommunity() {
		Setting setting = new Setting();
		communityService.saveSetting(InitCommunityTest.FAKE_COMMUNITY_ID, setting);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = IllegalArgumentException.class)
	public void saveSettingNotConfirmedCommunity() {
		Setting setting = new Setting();
		communityService.saveSetting(InitCommunityTest.NOTCONFIRMED_COMMUNITY_ID, setting);
	}
	
	@Test(enabled = true, priority = 13)
	public void saveSetting() {
		Account account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertNotNull(account);
		Assert.assertNotNull(account.getSetting());
		
		communityService.saveSetting(InitCommunityTest.COMMUNITY_ID, account.getSetting());
	}
	
	@Test(enabled = true, priority = 21, expectedExceptions = IllegalArgumentException.class)
	public void saveProfileFakeCommunity() {
		Profile profile = new Profile();
		profile.setDesc("description");
		
		communityService.saveProfile(InitCommunityTest.FAKE_COMMUNITY_ID, profile);
	}
	
	@Test(enabled = true, priority = 22, expectedExceptions = IllegalArgumentException.class)
	public void saveProfileNotConfirmedCommunity() {
		Profile profile = new Profile();
		profile.setDesc("description");
		
		communityService.saveProfile(InitCommunityTest.NOTCONFIRMED_COMMUNITY_ID, profile);
	}
	
	@Test(enabled = true, priority = 23)
	public void saveProfile() {
		Account account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertNotNull(account);
		Assert.assertNotNull(account.getProfile());
		account.getProfile().setDesc("description");
		
		communityService.saveProfile(InitCommunityTest.COMMUNITY_ID, account.getProfile());
		
		account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertNotNull(account);
		Assert.assertNotNull(account.getProfile());
		Assert.assertNotNull(account.getProfile().getDesc());
	}
	
	@Test(enabled = true, priority = 24)
	public void updateProfile() {
		Account account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertNotNull(account);
		Assert.assertNotNull(account.getProfile());
		CommunityProfile profile = (CommunityProfile) account.getProfile();
		profile.setSkype("unknown");
		
		communityService.saveProfile(InitCommunityTest.COMMUNITY_ID, profile);
		
		account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertNotNull(account);
		Assert.assertNotNull(account.getProfile());
		Assert.assertNotNull(account.getProfile().getDesc());
		Assert.assertNotNull(((CommunityProfile) account.getProfile()).getSkype());
	}
	
	@Test(enabled = true, priority = 91, expectedExceptions = IllegalArgumentException.class)
	public void deleteFakeCommunity() {
		communityService.delete(InitCommunityTest.FAKE_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 92, expectedExceptions = IllegalArgumentException.class)
	public void deleteUserNotConfirmedCommunity() {
		communityService.delete(InitCommunityTest.NOTCONFIRMED_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 93)
	public void deleteCommunity() {
		communityService.delete(InitCommunityTest.COMMUNITY_ID);
		Account account = communityRepository.findOne(InitCommunityTest.COMMUNITY_ID);
		Assert.assertTrue(account.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
