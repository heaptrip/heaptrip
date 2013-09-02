package com.heaptrip.service.account.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.account.user.UserRelationsService;

@Service
public class UserRelationsServiceImpl implements UserRelationsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	protected static final Logger logger = LoggerFactory.getLogger(UserRelationsServiceImpl.class);
	
	@Override
	public void sendFriendshipRequest(String userId, String friendId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(friendId, "friendId must not be null");
		
		User user = (User) userRepository.findOne(userId);
		User friend = (User) userRepository.findOne(friendId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (friend == null) {
			String msg = String.format("friend not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!friend.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("friend status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			Notification notification = new Notification();
			notification.setFromId(friendId);
			notification.setToId(userId);
			notification.setType(NotificationTypeEnum.FRIEND);
			notificationService.addNotification(notification);
		}
	}

	@Override
	public void deleteFriend(String userId, String friendId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(friendId, "publisherId must not be null");
		
		User user = (User) userRepository.findOne(userId);
		User friend = (User) userRepository.findOne(friendId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			return;
		} else if (friend == null) {
			String msg = String.format("friend not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!friend.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			return;
		} else {
			userRepository.deleteFriend(userId, friendId);
		}
	}

	@Override
	public void addPublisher(String userId, String publisherId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(publisherId, "publisherId must not be null");
		
		User user = (User) userRepository.findOne(userId);
		User publisher = (User) userRepository.findOne(publisherId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (publisher == null) {
			String msg = String.format("publisher not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!publisher.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("publisher status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			userRepository.addPublisher(userId, publisherId);
		}
	}

	@Override
	public void deletePublisher(String userId, String publisherId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(publisherId, "publisherId must not be null");
		
		User user = (User) userRepository.findOne(userId);
		User publisher = (User) userRepository.findOne(publisherId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			return;
		} else if (publisher == null) {
			String msg = String.format("publisher not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!publisher.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			return;
		} else {
			userRepository.deletePublisher(userId, publisherId);
		}
	}
}
