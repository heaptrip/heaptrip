package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.service.content.trip.TripUserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
public class CommunityDeleteTest extends AbstractTestNGSpringContextTests {

    private String TRIP_ID = "TripForCommunityDeleteTest";

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private CommunityRepository communityRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TripRepository tripRepository;

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        contentRepository.remove(TRIP_ID);
    }

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

    @Test(enabled = true, priority = 4, expectedExceptions = AccountException.class)
    public void deleteClubWithActiveContent() {
        Trip trip = new Trip();
        trip.setId(TRIP_ID);
        trip.setOwnerId(CommunityDataProvider.COMMUNITY_ID);
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
        trip.setStatus(status);
        contentRepository.save(trip);

        communityService.delete(CommunityDataProvider.COMMUNITY_ID);
    }

	@Test(enabled = true, priority = 5)
	public void deleteClub() {
        String[] allowed = new String[0];
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.DELETED);
        contentRepository.setStatus(TRIP_ID, status, allowed);

		communityService.delete(CommunityDataProvider.COMMUNITY_ID);

		Community community = communityRepository.findOne(CommunityDataProvider.COMMUNITY_ID);
		Assert.assertTrue(community.getStatus().equals(AccountStatusEnum.DELETED));
	}
}
