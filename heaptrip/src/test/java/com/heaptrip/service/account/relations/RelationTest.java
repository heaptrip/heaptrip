package com.heaptrip.service.account.relations;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.notification.NotificationService;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RelationTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RelationService relationService;

    @Autowired
    private RelationRepository relationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Test(enabled = true, priority = 1, expectedExceptions = AccountException.class)
	public void addPublisherFakeUser() {
		relationService.addPublisher(UserDataProvider.FAKE_USER_ID, UserDataProvider.EMAIL_USER_ID);
	}

	@Test(enabled = true, priority = 2, expectedExceptions = AccountException.class)
	public void addPublisherFakeFriend() {
		relationService.addPublisher(UserDataProvider.EMAIL_USER_ID, UserDataProvider.FAKE_USER_ID);
	}

	@Test(enabled = true, priority = 3, expectedExceptions = AccountException.class)
	public void addPublisherNotConfirmedUser() {
		relationService.addPublisher(UserDataProvider.NOT_CONFIRMED_USER_ID, UserDataProvider.EMAIL_USER_ID);
	}

	@Test(enabled = true, priority = 4, expectedExceptions = AccountException.class)
	public void addPublisherNotConfirmedFriend() {
		relationService.addPublisher(UserDataProvider.EMAIL_USER_ID, UserDataProvider.NOT_CONFIRMED_USER_ID);
	}

	@Test(enabled = true, priority = 5)
	public void addPublisherNetUser() {
		relationService.addPublisher(UserDataProvider.EMAIL_USER_ID, UserDataProvider.NET_USER_ID);

        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                                                                                            UserDataProvider.NET_USER_ID,
                                                                                            TypeRelationEnum.PUBLISHER));

        Assert.assertTrue(relations.size() == 1);
	}

	@Test(enabled = true, priority = 6)
	public void deletePublisherNetUser() {
		relationService.deletePublisher(UserDataProvider.EMAIL_USER_ID, UserDataProvider.NET_USER_ID);

        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                                                                                            UserDataProvider.NET_USER_ID,
                                                                                            TypeRelationEnum.PUBLISHER));

        Assert.assertTrue(relations.size() == 0);
	}

//	@Test(enabled = true, priority = 11, expectedExceptions = AccountException.class)
//	public void sendFriendshipRequestFakeUser() {
//		relationService.sendFriendshipRequest(InitRelationTest.FAKE_USER_ID, InitRelationTest.USER_PETROV_ID);
//	}
//
//	@Test(enabled = true, priority = 12, expectedExceptions = AccountException.class)
//	public void sendFriendshipRequestFakeFriend() {
//		relationService.sendFriendshipRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.FAKE_USER_ID);
//	}
//
//	@Test(enabled = true, priority = 13, expectedExceptions = AccountException.class)
//	public void sendFriendshipRequestNotConfirmedUser() {
//		relationService.sendFriendshipRequest(InitRelationTest.NOTCONFIRMED_USER_ID, InitRelationTest.USER_PETROV_ID);
//	}
//
//	@Test(enabled = true, priority = 14, expectedExceptions = AccountException.class)
//	public void sendFriendshipRequestNotConfirmedFriend() {
//		relationService.sendFriendshipRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.NOTCONFIRMED_USER_ID);
//	}
//
//	@Test(enabled = true, priority = 15)
//	public void sendFriendshipPetrov() {
//		// send friendship request
//		relationService.sendFriendshipRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.USER_PETROV_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.USER_PETROV_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.FRIEND.toString());
//
//		// accept friendship
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
//				break;
//			}
//		}
//
//		// check, petrov ivanov`s friend?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_PETROV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getFriend());
//
//		Boolean find = false;
//		for (String s : user.getFriend()) {
//			if (s.compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertTrue(find);
//	}
//
//	@Test(enabled = true, priority = 16)
//	public void sendFriendshipSidorov() {
//		// send friendship request
//		relationService.sendFriendshipRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.USER_SIDOROV_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.USER_SIDOROV_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.FRIEND.toString());
//
//		// accept friendship
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
//				break;
//			}
//		}
//
//		// check, sidorov not ivanov`s friend?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_SIDOROV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getFriend());
//
//		Boolean find = false;
//		for (String s : user.getFriend()) {
//			if (s.compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertFalse(find);
//	}
//
//	@Test(enabled = true, priority = 21, expectedExceptions = AccountException.class)
//	public void sendMemberRequestFakeUser() {
//		relationService.sendMemberRequest(InitRelationTest.FAKE_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
//	public void sendMemberRequestFakeCommunity() {
//		relationService.sendMemberRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.FAKE_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 23, expectedExceptions = AccountException.class)
//	public void sendMemberRequestNotConfirmedUser() {
//		relationService.sendMemberRequest(InitRelationTest.NOTCONFIRMED_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 24, expectedExceptions = AccountException.class)
//	public void sendMemberRequestNotConfirmedCommunity() {
//		relationService.sendMemberRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.NOTCONFIRMED_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 25)
//	public void sendMemberIvanov() {
//		// send member request
//		relationService.sendMemberRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.MEMBER.toString());
//
//		// accept member
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
//				break;
//			}
//		}
//
//		// check, ivanov is member?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_IVANOV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getMember());
//
//		Boolean find = false;
//		for (String s : user.getMember()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertTrue(find);
//	}
//
//	@Test(enabled = true, priority = 26)
//	public void sendMemberSidorov() {
//		// send member request
//		relationService.sendMemberRequest(InitRelationTest.USER_SIDOROV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.MEMBER.toString());
//
//		// reject member
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_SIDOROV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
//				break;
//			}
//		}
//
//		// check, sidorov is not member?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_SIDOROV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getMember());
//
//		Boolean find = false;
//		for (String s : user.getMember()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertFalse(find);
//	}
//
//	@Test(enabled = true, priority = 31, expectedExceptions = AccountException.class)
//	public void sendEmployeeRequestFakeUser() {
//		relationService.sendEmployeeRequest(InitRelationTest.FAKE_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 32, expectedExceptions = AccountException.class)
//	public void sendEmployeeRequestFakeCommunity() {
//		relationService.sendEmployeeRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.FAKE_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
//	public void sendEmployeeRequestNotConfirmedUser() {
//		relationService.sendEmployeeRequest(InitRelationTest.NOTCONFIRMED_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
//	public void sendEmployeeRequestNotConfirmedCommunity() {
//		relationService.sendEmployeeRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.NOTCONFIRMED_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 35)
//	public void sendEmployeeIvanov() {
//		// send employee request
//		relationService.sendEmployeeRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.EMPLOYEE.toString());
//
//		// accept employee
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
//				break;
//			}
//		}
//
//		// check, ivanov is employee?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_IVANOV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getEmployee());
//
//		Boolean find = false;
//		for (String s : user.getEmployee()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertTrue(find);
//	}
//
//	@Test(enabled = true, priority = 36)
//	public void sendEmployeeSidorov() {
//		// send employee request
//		relationService.sendEmployeeRequest(InitRelationTest.USER_SIDOROV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.EMPLOYEE.toString());
//
//		// reject employee
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_SIDOROV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
//				break;
//			}
//		}
//
//		// check, sidorov is not employee?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_SIDOROV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getEmployee());
//
//		Boolean find = false;
//		for (String s : user.getEmployee()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertFalse(find);
//	}
//
//	@Test(enabled = true, priority = 41, expectedExceptions = AccountException.class)
//	public void sendOwnerRequestFakeUser() {
//		relationService.sendOwnerRequest(InitRelationTest.FAKE_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 42, expectedExceptions = AccountException.class)
//	public void sendOwnerRequestFakeCommunity() {
//		relationService.sendOwnerRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.FAKE_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 43, expectedExceptions = AccountException.class)
//	public void sendOwnerRequestNotConfirmedUser() {
//		relationService.sendOwnerRequest(InitRelationTest.NOTCONFIRMED_USER_ID, InitRelationTest.COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 44, expectedExceptions = AccountException.class)
//	public void sendOwnerRequestNotConfirmedCommunity() {
//		relationService.sendOwnerRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.NOTCONFIRMED_COMMUNITY_ID);
//	}
//
//	@Test(enabled = true, priority = 45)
//	public void sendOwnerIvanov() {
//		// send owner request
//		relationService.sendOwnerRequest(InitRelationTest.USER_IVANOV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.OWNER.toString());
//
//		// accept owner
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_IVANOV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);
//				break;
//			}
//		}
//
//		// check, ivanov is owner?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_IVANOV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getOwner());
//
//		Boolean find = false;
//		for (String s : user.getOwner()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertTrue(find);
//	}
//
//	@Test(enabled = true, priority = 46)
//	public void sendOwnerSidorov() {
//		// send owner request
//		relationService.sendOwnerRequest(InitRelationTest.USER_SIDOROV_ID, InitRelationTest.COMMUNITY_ID);
//
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		criteria.setStatus(NotificationStatusEnum.NEW.toString());
//		criteria.setType(NotificationTypeEnum.OWNER.toString());
//
//		// reject owner
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		Assert.assertNotNull(list);
//		Assert.assertTrue(!list.isEmpty());
//
//		String notificationId = null;
//
//		for (Notification notification : list) {
//			if (notification.getFromId().compareTo(InitRelationTest.USER_SIDOROV_ID) == 0) {
//				notificationId = notification.getId();
//				notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);
//				break;
//			}
//		}
//
//		// check, sidorov is not owner?
//		Assert.assertNotNull(notificationId);
//
//		Notification notification = notificationRepository.findOne(notificationId);
//		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));
//
//		User user = userRepository.findOne(InitRelationTest.USER_SIDOROV_ID);
//		Assert.assertNotNull(user);
//		Assert.assertNotNull(user.getOwner());
//
//		Boolean find = false;
//		for (String s : user.getOwner()) {
//			if (s.compareTo(InitRelationTest.COMMUNITY_ID) == 0) {
//				find = true;
//				break;
//			}
//		}
//
//		Assert.assertFalse(find);
//	}
//
//	@Test(enabled = true, priority = 50)
//	public void clearAfterTest() {
//		// ivanov
//		NotificationCriteria criteria = new NotificationCriteria();
//		criteria.setToId(InitRelationTest.USER_IVANOV_ID);
//		List<Notification> list = notificationService.getNotifications(criteria);
//
//		// petrov
//		criteria.setToId(InitRelationTest.USER_PETROV_ID);
//		list.addAll(notificationService.getNotifications(criteria));
//		// sidorov
//		criteria.setToId(InitRelationTest.USER_SIDOROV_ID);
//		list.addAll(notificationService.getNotifications(criteria));
//		// community
//		criteria.setToId(InitRelationTest.COMMUNITY_ID);
//		list.addAll(notificationService.getNotifications(criteria));
//		// clear
//		for (Notification notification : list) {
//			notificationRepository.remove(notification.getId());
//		}
//	}
}
