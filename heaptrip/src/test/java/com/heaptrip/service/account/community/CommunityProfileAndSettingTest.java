package com.heaptrip.service.account.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.community.CommunityProfile;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.domain.service.account.community.CommunityService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CommunityProfileAndSettingTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private CommunityRepository communityRepository;

    // чтение, изменение и удаление Profile & Setting тестируются в UserProfileAndSettingTest

	@Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
	public void deleteFakeCommunity() {
		communityService.delete(CommunityDataProvider.FAKE_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
	public void deleteNotConfirmedCommunity() {
		communityService.delete(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
	}

    @Test(enabled = true, priority = 3, expectedExceptions = AccountException.class)
    public void deleteDeletedCommunity() {
        communityService.delete(CommunityDataProvider.DELETED_COMMUNITY_ID);
    }
	
	@Test(enabled = true, priority = 4)
	public void deleteClub() {
		communityService.delete(CommunityDataProvider.COMMUNITY_ID);
		Community community = communityRepository.findOne(CommunityDataProvider.COMMUNITY_ID);
		Assert.assertTrue(community.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
