package com.heaptrip.service.trip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.trip.TableInvite;
import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.heaptrip.domain.service.trip.TripService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripServiceUserTest extends AbstractTestNGSpringContextTests {

	private static String TRIP_ID = "0";

	private static String TABLE_ITEM_ID = "0";

	private static String USER_ID = "1";

	private static String USER_EMAIL = "test@test.test";

	@Autowired
	private TripService tripService;

	@Autowired
	private TripRepository tripRepository;

	@Test(priority = 1, enabled = true)
	public void addTableUser() {
		// call
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNull(trip.getTable()[0].getUsers());
		tripService.addTableUser(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		// check
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertTrue(trip.getTable()[0].getUsers().equals(1L));
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ITEM_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.INVITE);
	}

	@Test(priority = 2, enabled = true)
	public void removeTableUser() {
		// call
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		tripService.removeTripMember(member.getId());
		// check
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertTrue(trip.getTable()[0].getUsers().equals(0L));
		list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 0);
	}

	@Test(priority = 3, enabled = true)
	public void addTableInvite() {
		// call
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNull(trip.getTable()[0].getInvites());
		tripService.addTableInvite(TRIP_ID, TABLE_ITEM_ID, USER_EMAIL);
		// check
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getInvites());
		Assert.assertTrue(trip.getTable()[0].getInvites().equals(1L));
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableInvite);
		TableInvite invite = (TableInvite) member;
		Assert.assertEquals(invite.getTripId(), TRIP_ID);
		Assert.assertEquals(invite.getTableId(), TABLE_ITEM_ID);
		Assert.assertEquals(invite.getEmail(), USER_EMAIL);
		// remove
		tripService.removeTripMember(member.getId());
	}

	@Test(priority = 4, enabled = true)
	public void addTableRequest() {
		// call
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertTrue(trip.getTable()[0].getUsers().equals(0L));
		tripService.addTableRequest(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		// check
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertTrue(trip.getTable()[0].getUsers().equals(1L));
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ITEM_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.REQUEST);
	}

	@Test(priority = 5, enabled = true)
	public void acceptTableUser() {
		// call
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		tripService.acceptTableUser(member.getId());
		// check
		list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertEquals(user.getTripId(), TRIP_ID);
		Assert.assertEquals(user.getTableId(), TABLE_ITEM_ID);
		Assert.assertEquals(user.getUserId(), USER_ID);
		Assert.assertEquals(user.getStatus(), TableUserStatusEnum.OK);
	}

	@Test(priority = 6, enabled = true)
	public void setTableUserOrganizer() {
		// call
		List<TableMember> list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		TableMember member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		TableUser user = (TableUser) member;
		Assert.assertNull(user.getIsOrganizer());
		tripService.setTableUserOrganizer(member.getId(), true);
		// check
		list = tripService.getTableMembers(TRIP_ID, TABLE_ITEM_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 1);
		member = list.get(0);
		Assert.assertNotNull(member);
		Assert.assertTrue(member instanceof TableUser);
		user = (TableUser) member;
		Assert.assertTrue(user.getIsOrganizer());
	}
}
