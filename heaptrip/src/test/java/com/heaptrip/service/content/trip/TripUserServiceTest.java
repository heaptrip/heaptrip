package com.heaptrip.service.content.trip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.trip.TableInvite;
import com.heaptrip.domain.entity.content.trip.TableMember;
import com.heaptrip.domain.entity.content.trip.TableUser;
import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripUserServiceTest extends AbstractTestNGSpringContextTests {

	private String TRIP_ID = TripDataProvider.CONTENT_IDS[0];

	private String TABLE_ID = "0";

	private String USER_ID = TripDataProvider.USER_ID;

	private String ALLOWED_USER_ID = "TEST_ALLOWED_USER";

	private String USER_EMAIL = "test@test.test";

	@Autowired
	private TripUserService tripUserService;

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private TripFeedService tripFeedService;

	@Test(priority = 0, enabled = true)
	public void addTableUser() {
		// call
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNull(trip.getTable()[0].getMembers());
		tripUserService.addTableUser(TRIP_ID, TABLE_ID, USER_ID);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(1L));
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.INVITE);
	}

	@Test(priority = 1, enabled = true)
	public void removeTableUser() {
		// call
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		tripUserService.removeTripMember(member.getId());
		// check
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(0L));
		list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 0);
	}

	@Test(priority = 2, enabled = true)
	public void addTableInvite() {
		// call
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(0L));
		tripUserService.addTableInvite(TRIP_ID, TABLE_ID, USER_EMAIL);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(1L));
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableInvite);
		TableInvite invite = (TableInvite) member;
		Assert.assertEquals(invite.getTripId(), TRIP_ID);
		Assert.assertEquals(invite.getTableId(), TABLE_ID);
		Assert.assertEquals(invite.getEmail(), USER_EMAIL);
		// remove
		tripUserService.removeTripMember(member.getId());
	}

	@Test(priority = 3, enabled = true)
	public void addTableRequest() {
		// call
		Trip trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(0L));
		tripUserService.addTableRequest(TRIP_ID, TABLE_ID, USER_ID);
		// check
		trip = tripRepository.findOne(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getMembers());
		Assert.assertTrue(trip.getTable()[0].getMembers().equals(1L));
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.REQUEST);
	}

	@Test(priority = 4, enabled = true)
	public void acceptTableUser() {
		// call
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		tripUserService.acceptTableUser(member.getId());
		// check
		list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.OK);
	}

	@Test(priority = 5, enabled = true)
	public void setTableUserOrganizer() {
		// call
		List<TableMember> list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertNull(user.getIsOrganizer());
		tripUserService.setTableUserOrganizer(member.getId(), true);
		// check
		list = tripUserService.getTableMembers(TRIP_ID, TABLE_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		user = (TableUser) member;
		Assert.assertTrue(user.getIsOrganizer());
	}

	@Test(priority = 6, enabled = true)
	public void getTableUsersByUserId() {
		// call
		List<TableUser> list = tripUserService.getTableUsersByUserId(TRIP_ID, USER_ID);
		// check
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
	}

	@Test(priority = 7, enabled = true)
	public void isTableUser() {
		// call
		boolean result = tripUserService.isTableUser(TRIP_ID, USER_ID);
		// check
		Assert.assertTrue(result);
	}

	@Test(priority = 7, enabled = true, dataProvider = "memberMyAccountCriteria", dataProviderClass = TripDataProvider.class)
	public void getTripsByMemberCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
		// call
		List<Trip> trips = tripFeedService.getContentsByMyAccountCriteria(tripMyAccountCriteria);
		// check
		Assert.assertNotNull(trips);
		Assert.assertEquals(trips.size(), 1);
	}

	@Test(priority = 8, enabled = true, dataProvider = "memberMyAccountCriteria", dataProviderClass = TripDataProvider.class)
	public void getCountByMemberCriteria(TripMyAccountCriteria tripMyAccountCriteria) {
		// call
		long count = tripFeedService.getCountByMyAccountCriteria(tripMyAccountCriteria);
		// check
		Assert.assertNotNull(count);
		Assert.assertEquals(count, 1);
	}

	@Test(priority = 9, enabled = true, dataProvider = "feedCriteria", dataProviderClass = TripDataProvider.class)
	public void addAllowed(TripFeedCriteria tripFeedCriteria) {
		// call
		tripUserService.addAllowed(TripDataProvider.OWNER_ID, ALLOWED_USER_ID);
		// check
		tripFeedCriteria.setUserId(ALLOWED_USER_ID);
		long count = tripFeedService.getCountByFeedCriteria(tripFeedCriteria);
	}

	@Test(priority = 10, enabled = true, dataProvider = "feedCriteria", dataProviderClass = TripDataProvider.class)
	public void removeAllowed(TripFeedCriteria tripFeedCriteria) {
		// call
		tripUserService.removeAllowed(TripDataProvider.OWNER_ID, ALLOWED_USER_ID);
		// check
		tripFeedCriteria.setUserId(ALLOWED_USER_ID);
		long count = tripFeedService.getCountByFeedCriteria(tripFeedCriteria);
		Assert.assertNotEquals(count, TripDataProvider.CONTENT_IDS.length);
	}

}
