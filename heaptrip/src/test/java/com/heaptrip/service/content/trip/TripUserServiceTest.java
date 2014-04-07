package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.trip.*;
import com.heaptrip.domain.exception.trip.TripException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class TripUserServiceTest extends AbstractTestNGSpringContextTests {

    private String TRIP_ID = TripUserDataProvider.TRIP_ID;

    private String[] TABLE_IDs = TripUserDataProvider.TABLE_IDs;

    private String[] USER_IDs = TripUserDataProvider.USER_IDs;

    private String USER_ID = TripUserDataProvider.USER_IDs[0];

    private String USER_EMAIL = TripUserDataProvider.USER_EMAIL;

    @Autowired
    private TripUserService tripUserService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TripMemberRepository tripMemberRepository;

    //@Autowired
    //private NotificationService notificationService;

    @BeforeClass
    public void beforeClass() {
        // create test trip
        tripRepository.save(TripUserDataProvider.getTrip());
        // create test users
        User[] users = TripUserDataProvider.getUsers();
        for (User user : users) {
            accountRepository.save(user);
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // remove test trip
        tripRepository.remove(TRIP_ID);
        // remove test users
        User[] users = TripUserDataProvider.getUsers();
        for (User user : users) {
            accountRepository.remove(user);
        }
        // remove test notifications
        // TODO konovalov & dikma: remove notifications by test user ids
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        tripUserService.removeTripMembers(TRIP_ID);
    }

    @Test
    public void sendInvite() {
        // check before test
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[0]);
        if (trip.getTable()[0].getMembers() != null) {
            Assert.assertEquals(trip.getTable()[0].getMembers(), new Long(0));
        }
        // call
        TripUser user = tripUserService.sendInvite(TRIP_ID, TABLE_IDs[2], USER_ID);
        // check return object
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getContentId(), TRIP_ID);
        Assert.assertEquals(user.getTableId(), TABLE_IDs[2]);
        Assert.assertEquals(user.getUserId(), USER_ID);
        Assert.assertEquals(user.getStatus(), TableUserStatusEnum.INVITE);
        Assert.assertNull(user.getIsOrganizer());
        // check trip object
        trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[2]);
        Assert.assertNotNull(trip.getTable()[2].getMembers());
        Assert.assertEquals(trip.getTable()[2].getMembers(), Long.valueOf(1));
    }

    @Test
    public void sendExternalInvite() {
        // check before test
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[0]);
        if (trip.getTable()[0].getMembers() != null) {
            Assert.assertEquals(trip.getTable()[0].getMembers(), new Long(0));
        }
        // call
        TripInvite invite = tripUserService.sendExternalInvite(TRIP_ID, TABLE_IDs[1], USER_EMAIL);
        // check return object
        Assert.assertNotNull(invite);
        Assert.assertNotNull(invite.getId());
        Assert.assertEquals(invite.getContentId(), TRIP_ID);
        Assert.assertEquals(invite.getTableId(), TABLE_IDs[1]);
        Assert.assertEquals(invite.getEmail(), USER_EMAIL);
        // check trip object
        trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[1]);
        Assert.assertNotNull(trip.getTable()[1].getMembers());
        Assert.assertEquals(trip.getTable()[1].getMembers(), Long.valueOf(1));
    }

    @Test
    public void sendRequest() {
        // check before test
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[0]);
        if (trip.getTable()[0].getMembers() != null) {
            Assert.assertEquals(trip.getTable()[0].getMembers(), new Long(0));
        }
        // call
        TripUser user = tripUserService.sendRequest(TRIP_ID, TABLE_IDs[3], USER_ID);
        // check return object
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getContentId(), TRIP_ID);
        Assert.assertEquals(user.getTableId(), TABLE_IDs[3]);
        Assert.assertEquals(user.getUserId(), USER_ID);
        Assert.assertEquals(user.getStatus(), TableUserStatusEnum.REQUEST);
        // check trip object
        trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[3]);
        Assert.assertNotNull(trip.getTable()[3].getMembers());
        Assert.assertEquals(trip.getTable()[3].getMembers(), Long.valueOf(1));
    }

    @Test(expectedExceptions = TripException.class)
    public void sendTwiceInvite() {
        tripUserService.sendInvite(TRIP_ID, TABLE_IDs[2], USER_ID);
        tripUserService.sendInvite(TRIP_ID, TABLE_IDs[2], USER_ID);
    }

    @Test(expectedExceptions = TripException.class)
    public void sendTwiceExternalInvite() {
        tripUserService.sendExternalInvite(TRIP_ID, TABLE_IDs[1], USER_EMAIL);
        tripUserService.sendExternalInvite(TRIP_ID, TABLE_IDs[1], USER_EMAIL);
    }

    @Test(expectedExceptions = TripException.class)
    public void sendTwiceRequest() {
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[3], USER_ID);
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[3], USER_ID);
    }

    @Test
    public void acceptTripMemberByTripIdAndTableIdAndUserId() {
        // prepare
        TripUser user = tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_ID);
        Assert.assertFalse(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // call
        tripUserService.acceptTripMember(TRIP_ID, TABLE_IDs[1], USER_ID);
        // check by isTripAcceptedMember and isTripTableMember
        Assert.assertTrue(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // check member object
        TripMember member = tripMemberRepository.findOne(user.getId());
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        user = (TripUser) member;
        Assert.assertEquals(user.getStatus(), TableUserStatusEnum.OK);
    }


    @Test
    public void acceptTripMemberByMemberIdAfterSendInvite() {
        // prepare
        TripUser user = tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_ID);
        Assert.assertFalse(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // call
        tripUserService.acceptTripMember(user.getId());
        // check by isTripAcceptedMember and isTripTableMember
        Assert.assertTrue(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // check member object
        TripMember member = tripMemberRepository.findOne(user.getId());
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        user = (TripUser) member;
        Assert.assertEquals(user.getStatus(), TableUserStatusEnum.OK);
    }

    @Test
    public void acceptTripMemberByMemberIdAfterSendRequest() {
        // prepare
        TripUser user = tripUserService.sendRequest(TRIP_ID, TABLE_IDs[2], USER_ID);
        Assert.assertFalse(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // call
        tripUserService.acceptTripMember(user.getId());
        // check by isTripAcceptedMember and isTripTableMember
        Assert.assertTrue(tripUserService.isTripAcceptedMember(TRIP_ID, USER_ID));
        // check member object
        TripMember member = tripMemberRepository.findOne(user.getId());
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        user = (TripUser) member;
        Assert.assertEquals(user.getStatus(), TableUserStatusEnum.OK);
    }

    @Test
    public void setTripMemberOrganizer() {
        // prepare
        TripUser user = tripUserService.sendInvite(TRIP_ID, TABLE_IDs[3], USER_ID);
        tripUserService.acceptTripMember(user.getId());
        // call
        tripUserService.setTripMemberOrganizer(user.getId(), true);
        // check
        TripMember member = tripMemberRepository.findOne(user.getId());
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        user = (TripUser) member;
        Assert.assertTrue(user.getIsOrganizer());
    }

    @Test
    public void getByCriteriaWithTripId() {
        // add test data
        List<TripUser> addUsers = new ArrayList<>();
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_IDs[0]));
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[3], USER_IDs[1]));
        // prepare criteria
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        // call get members
        List<TripMember> members = tripUserService.getMembersByCriteria(memberCriteria);
        // check return objects
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 2);
        for (TripMember member : members) {
            Assert.assertNotNull(member);
            Assert.assertTrue(member instanceof TripUser);
            TripUser readUser = (TripUser) member;
            Assert.assertTrue(addUsers.contains(readUser));
            if (readUser.equals(addUsers.get(0))) {
                Assert.assertEquals(readUser.getContentId(), TRIP_ID);
                Assert.assertEquals(readUser.getTableId(), TABLE_IDs[1]);
                Assert.assertEquals(readUser.getUserId(), USER_IDs[0]);
                Assert.assertEquals(readUser.getStatus(), TableUserStatusEnum.INVITE);
            } else {
                Assert.assertEquals(readUser.getContentId(), TRIP_ID);
                Assert.assertEquals(readUser.getTableId(), TABLE_IDs[3]);
                Assert.assertEquals(readUser.getUserId(), USER_IDs[1]);
                Assert.assertEquals(readUser.getStatus(), TableUserStatusEnum.INVITE);
            }
        }
        // call get count
        long count = tripUserService.getCountByCriteria(memberCriteria);
        // check count
        Assert.assertEquals(count, 2);
    }

    @Test
    public void getByCriteriaWithTripIdAndTableId() {
        // add test data
        List<TripUser> addUsers = new ArrayList<>();
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_IDs[0]));
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[3], USER_IDs[1]));
        // prepare criteria
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        memberCriteria.setTableId(TABLE_IDs[3]);
        // call get members
        List<TripMember> members = tripUserService.getMembersByCriteria(memberCriteria);
        // check return objects
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 1);
        TripMember member = members.get(0);
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        TripUser readUser = (TripUser) member;
        Assert.assertEquals(readUser, addUsers.get(1));
        Assert.assertEquals(readUser.getContentId(), TRIP_ID);
        Assert.assertEquals(readUser.getTableId(), TABLE_IDs[3]);
        Assert.assertEquals(readUser.getUserId(), USER_IDs[1]);
        Assert.assertEquals(readUser.getStatus(), TableUserStatusEnum.INVITE);
        // call get count
        long count = tripUserService.getCountByCriteria(memberCriteria);
        // check count
        Assert.assertEquals(count, 1);
    }

    @Test
    public void getByCriteriaWithTripIdAndUserId() {
        // add test data
        List<TripUser> addUsers = new ArrayList<>();
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_IDs[0]));
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[3], USER_IDs[1]));
        // prepare criteria
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        memberCriteria.setUserId(USER_IDs[0]);
        // call get members
        List<TripMember> members = tripUserService.getMembersByCriteria(memberCriteria);
        // check return objects
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 1);
        TripMember member = members.get(0);
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        TripUser readUser = (TripUser) member;
        Assert.assertEquals(readUser, addUsers.get(0));
        Assert.assertEquals(readUser.getContentId(), TRIP_ID);
        Assert.assertEquals(readUser.getTableId(), TABLE_IDs[1]);
        Assert.assertEquals(readUser.getUserId(), USER_IDs[0]);
        Assert.assertEquals(readUser.getStatus(), TableUserStatusEnum.INVITE);
        // call get count
        long count = tripUserService.getCountByCriteria(memberCriteria);
        // check count
        Assert.assertEquals(count, 1);
    }

    @Test
    public void getByCriteriaWithTripIdAndTableIdAndUserId() {
        // add test data
        List<TripUser> addUsers = new ArrayList<>();
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[1], USER_IDs[0]));
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[3], USER_IDs[1]));
        addUsers.add(tripUserService.sendInvite(TRIP_ID, TABLE_IDs[4], USER_IDs[2]));
        // prepare criteria
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        memberCriteria.setTableId(TABLE_IDs[4]);
        memberCriteria.setUserId(USER_IDs[2]);
        // call get members
        List<TripMember> members = tripUserService.getMembersByCriteria(memberCriteria);
        // check return objects
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 1);
        TripMember member = members.get(0);
        Assert.assertNotNull(member);
        Assert.assertTrue(member instanceof TripUser);
        TripUser readUser = (TripUser) member;
        Assert.assertEquals(readUser, addUsers.get(2));
        Assert.assertEquals(readUser.getContentId(), TRIP_ID);
        Assert.assertEquals(readUser.getTableId(), TABLE_IDs[4]);
        Assert.assertEquals(readUser.getUserId(), USER_IDs[2]);
        Assert.assertEquals(readUser.getStatus(), TableUserStatusEnum.INVITE);
        // call get count
        long count = tripUserService.getCountByCriteria(memberCriteria);
        // check count
        Assert.assertEquals(count, 1);
    }

    @Test
    public void removeTripMemberByMemberId() {
        // prepare
        TripUser user = tripUserService.sendRequest(TRIP_ID, TABLE_IDs[2], USER_ID);
        // check before test
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        long count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 1);
        // call
        tripUserService.removeTripMember(user.getId());
        // check count
        count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 0);
        // check trip object
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[2]);
        Assert.assertNotNull(trip.getTable()[2].getMembers());
        Assert.assertTrue(trip.getTable()[2].getMembers().equals(0L));
    }

    @Test
    public void removeTripMemberByTripIdAndTableIdAndUserId() {
        // prepare
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[2], USER_ID);
        // check before test
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        long count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 1);
        Assert.assertTrue(tripUserService.isTripTableMember(TRIP_ID, TABLE_IDs[2], USER_ID));
        // call
        tripUserService.removeTripMember(TRIP_ID, TABLE_IDs[2], USER_ID);
        // check count
        count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 0);
        Assert.assertFalse(tripUserService.isTripTableMember(TRIP_ID, TABLE_IDs[2], USER_ID));
        // check trip object
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        Assert.assertNotNull(trip.getTable()[2]);
        Assert.assertNotNull(trip.getTable()[2].getMembers());
        Assert.assertTrue(trip.getTable()[2].getMembers().equals(0L));
    }

    @Test
    public void removeTripMembers() {
        // prepare
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[0], USER_ID);
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[1], USER_ID);
        tripUserService.sendRequest(TRIP_ID, TABLE_IDs[2], USER_ID);
        // check before test
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(TRIP_ID);
        long count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 3);
        // call
        tripUserService.removeTripMembers(TRIP_ID);
        // check count
        count = tripUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 0);
        // check trip object
        Trip trip = tripRepository.findOne(TRIP_ID);
        Assert.assertNotNull(trip);
        Assert.assertNotNull(trip.getTable());
        for (TableItem item : trip.getTable()) {
            Assert.assertNotNull(item);
            if (item.getMembers() != null) {
                Assert.assertEquals(item.getMembers(), Long.valueOf(0));
            }
        }
    }

}
