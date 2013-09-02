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
	
	@Test(enabled = true, priority = 1, expectedExceptions = IllegalArgumentException.class)
	public void addPublisherFakeUser() {
		userRelationsService.addPublisher(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 2, expectedExceptions = IllegalArgumentException.class)
	public void addPublisherFakeFriend() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_USER_ID);
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertTrue(user.getPublishers().length == 0);
	}
	
	@Test(enabled = true, priority = 3, expectedExceptions = IllegalArgumentException.class)
	public void addPublisherNotConfirmedUser() {
		userRelationsService.addPublisher(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 4, expectedExceptions = IllegalArgumentException.class)
	public void addPublisherNotConfirmedFriend() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_USER_ID);
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertTrue(user.getPublishers().length == 0);
	}
	
	@Test(enabled = true, priority = 5)
	public void addPublisherPetrov() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		Boolean find = false;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		if (user.getPublishers() != null) {
			for (String accountId : user.getPublishers()) {
				accountId.equals(InitAccountRelationsTest.USER_PETROV_ID);
				find = true;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 5)
	public void addPublisherSidorov() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_SIDOROV_ID);
		
		Boolean find = false;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		if (user.getPublishers() != null) {
			for (String accountId : user.getPublishers()) {
				accountId.equals(InitAccountRelationsTest.USER_SIDOROV_ID);
				find = true;
			}
		}
		
		Assert.assertTrue(find);
	}
	
	@Test(enabled = true, priority = 6)
	public void addPublisherPetrovRepeat() {
		userRelationsService.addPublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}

	@Test(enabled = true, priority = 7)
	public void deletePublisherPetrov() {
		userRelationsService.deletePublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		Boolean notFind = true;
		
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		if (user.getPublishers() != null) {
			for (String accountId : user.getPublishers()) {
				if (accountId.equals(InitAccountRelationsTest.USER_PETROV_ID)) {
					notFind = true;
					break;
				}
			}
		}
		
		Assert.assertTrue(notFind);
	}
	
	@Test(enabled = true, priority = 8)
	public void deletePublisherPetrovRepeat() {
		userRelationsService.deletePublisher(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = IllegalArgumentException.class)
	public void sendFriendshipRequestFakeUser() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.FAKE_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 12, expectedExceptions = IllegalArgumentException.class)
	public void sendFriendshipRequestFakeFriend() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.FAKE_USER_ID);
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertTrue(user.getFriends().length == 0);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = IllegalArgumentException.class)
	public void sendFriendshipRequestNotConfirmedUser() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.NOTCONFIRMED_USER_ID, InitAccountRelationsTest.USER_PETROV_ID);
	}
	
	@Test(enabled = true, priority = 14, expectedExceptions = IllegalArgumentException.class)
	public void sendFriendshipRequestNotConfirmedFriend() {
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.NOTCONFIRMED_USER_ID);
		User user = (User) userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertTrue(user.getFriends().length == 0);
	}
	
	@Test(enabled = true, priority = 15)
	public void sendFriendshipPetrov() {
		// clear trash 
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_IVANOV_ID);
		
		List<Notification> list = notificationService.getNotifications(criteria);
		
		for (Notification notification : list) {
			notificationRepository.remove(notification.getId());
		}
		
		// send friendship request
		userRelationsService.sendFriendshipRequest(InitAccountRelationsTest.USER_IVANOV_ID, InitAccountRelationsTest.USER_PETROV_ID);
		
		criteria = new NotificationCriteria();
		criteria.setToId(InitAccountRelationsTest.USER_IVANOV_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.FRIEND.toString());
		
		// accept friendship
		list = notificationService.getNotifications(criteria);
		
		Assert.assertNotNull(list);
		Assert.assertTrue(!list.isEmpty());
		
		String notificationId = null;
		
		for (Notification notification : list) {
			if (notification.getFromId().compareTo(InitAccountRelationsTest.USER_PETROV_ID) == 0) {
				notificationId = notification.getId();
				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
				break;
			}
		}
		
		// check, petrov ivanov`s friend?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getFriends());
		
		Boolean find = false;
		for (String s : user.getFriends()) {
			if (s.compareTo(InitAccountRelationsTest.USER_PETROV_ID) == 0) {
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
		criteria.setToId(InitAccountRelationsTest.USER_IVANOV_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.FRIEND.toString());
		
		// accept friendship
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
		
		// check, sidorov not ivanov`s friend?
		Assert.assertNotNull(notificationId);
		
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
		
		User user = userRepository.findOne(InitAccountRelationsTest.USER_IVANOV_ID);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getFriends());
		
		Boolean find = false;
		for (String s : user.getFriends()) {
			if (s.compareTo(InitAccountRelationsTest.USER_SIDOROV_ID) == 0) {
				find = true;
				break;
			}
		}
		
		Assert.assertFalse(find);
	}
}
