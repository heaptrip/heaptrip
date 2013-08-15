package com.heaptrip.service.account.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.service.account.community.CommunityService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitCommunityTest  extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private CommunityService communityService;
	
	public static String COMMUNITY_ID = "club number one";
	public static String COMMUNITY_EMAIL = "support@heaptrip.com";
	
	public static String NOTCONFIRMED_COMMUNITY_ID = "notConfirmedClub";
	public static String NOTCONFIRMED_COMMUNITY_EMAIL = "notConfirmedClub@example.com";
	
	public static String FAKE_COMMUNITY_ID = "fakeClub";
	public static String FAKE_COMMUNITY_EMAIL = "somebody@example.com";

	@BeforeTest()
	public void init() throws Exception {
		
		this.springTestContextPrepareTestInstance();
		
		Club club = new Club();
		club.setId(COMMUNITY_ID);
		club.setName("Club number one");
		club.setEmail(COMMUNITY_EMAIL);
		communityService.registration(club);
		
		Club notConfirmedClub = new Club();
		notConfirmedClub.setId(NOTCONFIRMED_COMMUNITY_ID);
		notConfirmedClub.setName("not Confirmed Club");
		notConfirmedClub.setEmail(NOTCONFIRMED_COMMUNITY_EMAIL);
		communityService.registration(notConfirmedClub);
	}
	
	@AfterTest
	public void afterTest() {
		communityService.hardRemove(NOTCONFIRMED_COMMUNITY_ID);
		communityService.hardRemove(COMMUNITY_ID);
	}
}
