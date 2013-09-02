package com.heaptrip.service.account.notification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationStatusEnum;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	protected static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void addNotification(Notification notification) {
		Assert.notNull(notification, "notification must not be null");
		notificationRepository.save(notification);
	}

	@Override
	public List<Notification> getNotifications(NotificationCriteria сriteria) {
		Assert.notNull(сriteria, "notificationCriteria must not be null");
		return notificationRepository.getNotificationsByCriteria(сriteria);
	}

	@Override
	public void changeStatus(String notificationId, NotificationStatusEnum status) {
		Assert.notNull(notificationId, "notificationId must not be null");
		
		Notification notification = notificationRepository.findOne(notificationId);
		
		if (notification == null) {
			String msg = String.format("notification not find by id %s", notificationId);
			logger.debug(msg);			
			// TODO dikma: заменить на бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!notification.getStatus().equals(NotificationStatusEnum.NEW)) {
			String msg = String.format("notification status must be: %s", NotificationStatusEnum.NEW);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			notificationRepository.changeStatus(notificationId, status);
			
			if (status.equals(NotificationStatusEnum.ACCEPTED)) {
				if (notification.getType().equals(NotificationTypeEnum.FRIEND)) {
					userRepository.addFriend(notification.getToId(), notification.getFromId());
				}
			}
		}
	}
}
