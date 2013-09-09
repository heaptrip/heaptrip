package com.heaptrip.service.account.relations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.account.user.UserRelationsService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserRelationsServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private UserRelationsService userRelationsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
	public void addPublisherFakeUser() {
		userRelationsService.addPublisher(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
	public void addPublisherFakeFriend() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_USER_ID);
	}
	
	@Test(enabled = true, priority = 3, expectedExceptions = AccountException.class)
	public void addPublisherNotConfirmedUser() {
		userRelationsService.addPublisher(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 4, expectedExceptions = AccountException.class)
	public void addPublisherNotConfirmedFriend() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_USER_ID);
	}
	
	@Test(enabled = true, priority = 5)
	public void addPublisherPetrov() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		Boolean find = false;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_PETROV_ID);
		if (user.getPublisher() != null) {
			for (String accountId : user.getPublisher()) {
				accountId.equals(InitAccountRelationsTest.USER_IVANOV_ID);
				find = true;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 6)
	public void addPublisherSidorov() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_SIDOROV_ID);
		
		Boolean find = false;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_SIDOROV_ID);
		if (user.getPublisher() != null) {
			for (String accountId : user.getPublisher()) {
				accountId.equals(InitAccountRelationsTest.USER_IVANOV_ID);
				find = true;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 7)
	public void addPublisherPetrovRepeat() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}

	@Test(enabled = true, priority = 8)
	public void deletePublisherPetrov() {
		userRelationsService.deletePublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		Boolean notFind = true;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_PETROV_ID);
		if (user.getPublisher() != null) {
			for (String accountId : user.getPublisher()) {
				if (accountId.equals(InitAccountRelationsTest.USER_IVANOV_ID)) {
					notFind = true;
					break;
				}
			}
		}
		
		Assert.assertTrue(notFind);
	}
	
	@Test(enabled = true, priority = 9)
	public void deletePublisherPetrovRepeat() {
		userRelationsService.deletePublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 10)
	public void clearBeforeTest() {
		// ivanov
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_IVANOV_ID);
		List<Notification> list = notificationService.getNotifications(criteria);

		// petrov
		criteria.setToId(InitAccountRelationsTest.USER_PETROV_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// sidorov
		criteria.setToId(InitAccountRelationsTest.USER_SIDOROV_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// community
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// clear
		for (Notification notification : list) {
			notificationRepository.remove(notification.getId());
		}
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestFakeUser() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestFakeFriend() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_USER_ID);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestNotConfirmedUser() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 14, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestNotConfirmedFriend() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_USER_ID);
	}
	
	@Test(enabled = true, priority = 15)
	public void sendFriendshipPetrov() {
		// send friendship request
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_PETROV_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.FRIEND.toString());
		
		// accept friendship
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
				break;
			}
		}
		
		// check, petrov ivanov`s friend?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_PETROV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getFriend());
		
		Boolean find = false;
		for (String s : user.getFriend()) {
			if (s.compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 16)
	public void sendFriendshipSidorov() {
		// send friendship request
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_SIDOROV_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_SIDOROV_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.FRIEND.toString());
		
		// accept friendship
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
				break;
			}
		}
		
		// check, sidorov not ivanov`s friend?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_SIDOROV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getFriend());
		
		Boolean find = false;
		for (String s : user.getFriend()) {
			if (s.compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertFalse(find);
	}
	
	@Test(enabled = true, priority = 21, expectedExceptions = AccountException.class)
	public void sendMemberRequestFakeUser() {
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
	public void sendMemberRequestFakeCommunity() {
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 23, expectedExceptions = AccountException.class)
	public void sendMemberRequestNotConfirmedUser() {
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 24, expectedExceptions = AccountException.class)
	public void sendMemberRequestNotConfirmedCommunity() {
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 25)
	public void sendMemberIvanov() {
		// send member request
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.MEMBER.toString());
		
		// accept member
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
				break;
			}
		}
		
		// check, ivanov is member?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getMember());
		
		Boolean find = false;
		for (String s : user.getMember()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 26)
	public void sendMemberSidorov() {
		// send member request
		userRelationsService.sendMemberRequest(InitAccountRelationsTest.USER_SIDOROV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.MEMBER.toString());
		
		// reject member
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_SIDOROV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
				break;
			}
		}
		
		// check, sidorov is not member?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_SIDOROV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getMember());
		
		Boolean find = false;
		for (String s : user.getMember()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertFalse(find);
	}
	
	@Test(enabled = true, priority = 31, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestFakeUser() {
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 32, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestFakeCommunity() {
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestNotConfirmedUser() {
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestNotConfirmedCommunity() {
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 35)
	public void sendEmployeeIvanov() {
		// send employee request
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.EMPLOYEE.toString());
		
		// accept employee
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
				break;
			}
		}
		
		// check, ivanov is employee?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getEmployee());
		
		Boolean find = false;
		for (String s : user.getEmployee()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 36)
	public void sendEmployeeSidorov() {
		// send employee request
		userRelationsService.sendEmployeeRequest(InitAccountRelationsTest.USER_SIDOROV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.EMPLOYEE.toString());
		
		// reject employee
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_SIDOROV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
				break;
			}
		}
		
		// check, sidorov is not employee?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_SIDOROV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getEmployee());
		
		Boolean find = false;
		for (String s : user.getEmployee()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertFalse(find);
	}
	
	@Test(enabled = true, priority = 41, expectedExceptions = AccountException.class)
	public void sendOwnerRequestFakeUser() {
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 42, expectedExceptions = AccountException.class)
	public void sendOwnerRequestFakeCommunity() {
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 43, expectedExceptions = AccountException.class)
	public void sendOwnerRequestNotConfirmedUser() {
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 44, expectedExceptions = AccountException.class)
	public void sendOwnerRequestNotConfirmedCommunity() {
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_COMMUNITY_ID);
	}
	
	@Test(enabled = true, priority = 45)
	public void sendOwnerIvanov() {
		// send owner request
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.OWNER.toString());
		
		// accept owner
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_IVANOV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
				break;
			}
		}
		
		// check, ivanov is owner?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getOwner());
		
		Boolean find = false;
		for (String s : user.getOwner()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 46)
	public void sendOwnerSidorov() {
		// send owner request
		userRelationsService.sendOwnerRequest(InitAccountRelationsTest.USER_SIDOROV_ID, InitAccountRelationsTest.COMMUNITY_ID);
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.OWNER.toString());
		
		// reject owner
		List<Notification> list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_SIDOROV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
				break;
			}
		}
		
		// check, sidorov is not owner?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_SIDOROV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getOwner());
		
		Boolean find = false;
		for (String s : user.getOwner()) {
			if (s.compareTo(InitAccountRelationsTest.COMMUNITY_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertFalse(find);
	}
	
	@Test(enabled = true, priority = 50)
	public void clearAfterTest() {
		// ivanov
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_IVANOV_ID);
		List<Notification> list = notificationService.getNotifications(criteria);

		// petrov
		criteria.setToId(InitAccountRelationsTest.USER_PETROV_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// sidorov
		criteria.setToId(InitAccountRelationsTest.USER_SIDOROV_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// community
		criteria.setToId(InitAccountRelationsTest.COMMUNITY_ID);
		list.addAll(notificationService.getNotifications(criteria));
		// clear
		for (Notification notification : list) {
			notificationRepository.remove(notification.getId());
		}
	}
}
