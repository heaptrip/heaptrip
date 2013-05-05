package com.heaptrip.service.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.trip.TableItem;
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

	@Test(dataProvider = "tableItem", dataProviderClass = TripDataProvider.class, enabled = true)
	public void getUserFromTableItem(TableItem tableItem) {
		TableUser user = tripService.getUserFromTableItem(tableItem, InitTripTest.USER_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user.getId(), InitTripTest.USER_ID);
	}

	@Test(priority = 1, enabled = true)
	public void addTableInvite() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNull(trip.getTable()[0].getUsers());
		tripService.addTableInvite(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getId());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getId(), USER_ID);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getStatus());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getStatus(), TableUserStatusEnum.INVITE);
	}

	@Test(priority = 2, enabled = true)
	public void removeTableUser() {
		tripService.removeTableUser(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertTrue(trip.getTable()[0].getUsers() == null || trip.getTable()[0].getUsers().length == 0);
	}

	@Test(priority = 3, enabled = true)
	public void addTableRequest() {
		tripService.addTableRequest(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getId());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getId(), USER_ID);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getStatus());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getStatus(), TableUserStatusEnum.REQUEST);
	}

	@Test(priority = 4, enabled = false)
	public void acceptTableUser() {
		tripService.acceptTableUser(TRIP_ID, TABLE_ITEM_ID, USER_ID);
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getId());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getId(), USER_ID);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getStatus());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getStatus(), TableUserStatusEnum.OK);
	}

	@Test(priority = 5, enabled = false)
	public void setTableUserOrganizer() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0]);
		Assert.assertNull(trip.getTable()[0].getUsers()[0].getIsOrganizer());
		tripService.setTableUserOrganizer(TRIP_ID, TABLE_ITEM_ID, USER_ID, true);
		trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers());
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0]);
		Assert.assertNotNull(trip.getTable()[0].getUsers()[0].getIsOrganizer());
		Assert.assertEquals(trip.getTable()[0].getUsers()[0].getIsOrganizer().booleanValue(), true);
	}

	@Test(priority = 6, enabled = false)
	public void addTableInviteToEmail() {
		Trip trip = tripRepository.findById(TRIP_ID);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNull(trip.getTable()[0].getInvites());
		tripService.addTableInviteToEmail(TRIP_ID, TABLE_ITEM_ID, USER_EMAIL);
		Assert.assertNotNull(trip);
		Assert.assertNotNull(trip.getTable());
		Assert.assertNotNull(trip.getTable()[0]);
		Assert.assertNotNull(trip.getTable()[0].getInvites());
		Assert.assertNotNull(trip.getTable()[0].getInvites()[0]);
		Assert.assertNotNull(trip.getTable()[0].getInvites()[0].getEmail());
		Assert.assertEquals(trip.getTable()[0].getInvites()[0].getEmail(), USER_EMAIL);
	}

}
