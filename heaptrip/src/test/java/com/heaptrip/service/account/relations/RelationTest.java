package com.heaptrip.service.account.relations;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.service.account.community.CommunityDataProvider;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.service.account.notification.NotificationService;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RelationTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RelationService relationService;

    @Autowired
    private RelationRepository relationRepository;
	
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

	@Test(enabled = true, priority = 11, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestFakeUser() {
		relationService.sendFriendshipRequest(UserDataProvider.FAKE_USER_ID, UserDataProvider.EMAIL_USER_ID);
	}

	@Test(enabled = true, priority = 12, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestFakeFriend() {
		relationService.sendFriendshipRequest(UserDataProvider.EMAIL_USER_ID, UserDataProvider.FAKE_USER_ID);
	}

	@Test(enabled = true, priority = 13, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestNotConfirmedUser() {
		relationService.sendFriendshipRequest(UserDataProvider.NOT_CONFIRMED_USER_ID, UserDataProvider.EMAIL_USER_ID);
	}

	@Test(enabled = true, priority = 14, expectedExceptions = AccountException.class)
	public void sendFriendshipRequestNotConfirmedFriend() {
		relationService.sendFriendshipRequest(UserDataProvider.EMAIL_USER_ID, UserDataProvider.NOT_CONFIRMED_USER_ID);
	}

	@Test(enabled = true, priority = 15)
	public void sendFriendship() {
		relationService.sendFriendshipRequest(UserDataProvider.EMAIL_USER_ID, UserDataProvider.NET_USER_ID);
        // ищем оповещение
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
		criteria.setToId(UserDataProvider.NET_USER_ID);
		criteria.setStatus(NotificationStatusEnum.NEW.toString());
		criteria.setType(NotificationTypeEnum.FRIEND.toString());

		List<Notification> notifications = notificationService.getNotificationsByNotificationCriteria(criteria);

        // проверим оповещение
		Assert.assertNotNull(notifications);
		Assert.assertTrue(!notifications.isEmpty());
        Assert.assertTrue(notifications.size() == 1);

		String notificationId = notifications.get(0).getId();

        Assert.assertNotNull(notificationId);

        // примем дружбу
        notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);

        // проверим что оповещение принято
		Notification notification = notificationRepository.findOne(notificationId);
		Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));

        // ищем связь
        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                                                                                            UserDataProvider.NET_USER_ID,
                                                                                            TypeRelationEnum.FRIEND));
        // проверим связь
        Assert.assertNotNull(relations);
        Assert.assertTrue(!relations.isEmpty());
        Assert.assertTrue(relations.size() == 1);
	}

	@Test(enabled = true, priority = 21, expectedExceptions = AccountException.class)
	public void sendMemberRequestFakeUser() {
		relationService.sendMemberRequest(UserDataProvider.FAKE_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
	public void sendMemberRequestFakeCommunity() {
		relationService.sendMemberRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.FAKE_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 23, expectedExceptions = AccountException.class)
	public void sendMemberRequestNotConfirmedUser() {
		relationService.sendMemberRequest(UserDataProvider.NOT_CONFIRMED_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 24, expectedExceptions = AccountException.class)
	public void sendMemberRequestNotConfirmedCommunity() {
		relationService.sendMemberRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 25)
	public void sendMemberRequestEmailUser() {
        relationService.sendMemberRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.COMMUNITY_ID);
        // ищем оповещение
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        criteria.setStatus(NotificationStatusEnum.NEW.toString());
        criteria.setType(NotificationTypeEnum.MEMBER.toString());

        List<Notification> notifications = notificationService.getNotificationsByNotificationCriteria(criteria);

        // проверим оповещение
        Assert.assertNotNull(notifications);
        Assert.assertTrue(!notifications.isEmpty());
        Assert.assertTrue(notifications.size() == 1);

        String notificationId = notifications.get(0).getId();

        Assert.assertNotNull(notificationId);

        // примем участника
        notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);

        // проверим что оповещение принято
        Notification notification = notificationRepository.findOne(notificationId);
        Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));

        // ищем связь
        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                                                                                            CommunityDataProvider.COMMUNITY_ID,
                                                                                            TypeRelationEnum.MEMBER));
        // проверим связь
        Assert.assertNotNull(relations);
        Assert.assertTrue(!relations.isEmpty());
        Assert.assertTrue(relations.size() == 1);
	}

	@Test(enabled = true, priority = 31, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestFakeUser() {
		relationService.sendEmployeeRequest(UserDataProvider.FAKE_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 32, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestFakeCommunity() {
		relationService.sendEmployeeRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.FAKE_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestNotConfirmedUser() {
		relationService.sendEmployeeRequest(UserDataProvider.NOT_CONFIRMED_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
	public void sendEmployeeRequestNotConfirmedCommunity() {
		relationService.sendEmployeeRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 35)
	public void sendEmployeeRequestEmailUser() {
        relationService.sendEmployeeRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.COMMUNITY_ID);
        // ищем оповещение
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        criteria.setStatus(NotificationStatusEnum.NEW.toString());
        criteria.setType(NotificationTypeEnum.EMPLOYEE.toString());

        List<Notification> notifications = notificationService.getNotificationsByNotificationCriteria(criteria);

        // проверим оповещение
        Assert.assertNotNull(notifications);
        Assert.assertTrue(!notifications.isEmpty());
        Assert.assertTrue(notifications.size() == 1);

        String notificationId = notifications.get(0).getId();

        Assert.assertNotNull(notificationId);

        // примем работника
        notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);

        // проверим что оповещение принято
        Notification notification = notificationRepository.findOne(notificationId);
        Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));

        // ищем связь
        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                CommunityDataProvider.COMMUNITY_ID,
                TypeRelationEnum.EMPLOYEE));
        // проверим связь
        Assert.assertNotNull(relations);
        Assert.assertTrue(!relations.isEmpty());
        Assert.assertTrue(relations.size() == 1);
	}

	@Test(enabled = true, priority = 41, expectedExceptions = AccountException.class)
	public void sendOwnerRequestFakeUser() {
		relationService.sendOwnerRequest(UserDataProvider.FAKE_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 42, expectedExceptions = AccountException.class)
	public void sendOwnerRequestFakeCommunity() {
		relationService.sendOwnerRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.FAKE_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 43, expectedExceptions = AccountException.class)
	public void sendOwnerRequestNotConfirmedUser() {
		relationService.sendOwnerRequest(UserDataProvider.NOT_CONFIRMED_USER_ID, CommunityDataProvider.COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 44, expectedExceptions = AccountException.class)
	public void sendOwnerRequestNotConfirmedCommunity() {
		relationService.sendOwnerRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
	}

	@Test(enabled = true, priority = 45)
	public void sendOwnerRequestEmailUser() {
        relationService.sendOwnerRequest(UserDataProvider.EMAIL_USER_ID, CommunityDataProvider.COMMUNITY_ID);
        // ищем оповещение
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        criteria.setStatus(NotificationStatusEnum.NEW.toString());
        criteria.setType(NotificationTypeEnum.OWNER.toString());

        List<Notification> notifications = notificationService.getNotificationsByNotificationCriteria(criteria);

        // проверим оповещение
        Assert.assertNotNull(notifications);
        Assert.assertTrue(!notifications.isEmpty());
        Assert.assertTrue(notifications.size() == 1);

        String notificationId = notifications.get(0).getId();

        Assert.assertNotNull(notificationId);

        // примем владельца
        notificationService.changeStatus(notificationId, NotificationStatusEnum.ACCEPTED);

        // проверим что оповещение принято
        Notification notification = notificationRepository.findOne(notificationId);
        Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.ACCEPTED));

        // ищем связь
        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                CommunityDataProvider.COMMUNITY_ID,
                TypeRelationEnum.OWNER));
        // проверим связь
        Assert.assertNotNull(relations);
        Assert.assertTrue(!relations.isEmpty());
        Assert.assertTrue(relations.size() == 1);
	}

	@Test(enabled = true, priority = 46)
	public void rejectOwnerRequestNetUser() {
        relationService.sendOwnerRequest(UserDataProvider.NET_USER_ID, CommunityDataProvider.COMMUNITY_ID);
        // ищем оповещение
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.NET_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        criteria.setStatus(NotificationStatusEnum.NEW.toString());
        criteria.setType(NotificationTypeEnum.OWNER.toString());

        List<Notification> notifications = notificationService.getNotificationsByNotificationCriteria(criteria);

        // проверим оповещение
        Assert.assertNotNull(notifications);
        Assert.assertTrue(!notifications.isEmpty());
        Assert.assertTrue(notifications.size() == 1);

        String notificationId = notifications.get(0).getId();

        Assert.assertNotNull(notificationId);

        // откажем владельцу
        notificationService.changeStatus(notificationId, NotificationStatusEnum.REJECTED);

        // проверим что оповещение НЕ принято
        Notification notification = notificationRepository.findOne(notificationId);
        Assert.assertTrue(notification.getStatus().equals(NotificationStatusEnum.REJECTED));

        // проверим что связи нет
        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.NET_USER_ID,
                CommunityDataProvider.COMMUNITY_ID,
                TypeRelationEnum.OWNER));
        // проверим связь
        Assert.assertNotNull(relations);
        Assert.assertTrue(relations.isEmpty());
	}
}
