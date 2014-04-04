package com.heaptrip.service.account.relation;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.notification.NotificationTypeEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.domain.service.system.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RelationServiceImpl implements RelationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommunityRepository communityRepository;

    @Autowired
	private RelationRepository relationRepository;

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private ErrorService errorService;
	
	@Autowired
	private AccountStoreService accountStoreService;
	
	protected static final Logger logger = LoggerFactory.getLogger(RelationServiceImpl.class);
	
	@Override
	public void sendFriendshipRequest(String userId, String friendId) {
        Assert.notNull(userId, "userId must not be null");
		Assert.notNull(friendId, "friendId must not be null");

		User user = userRepository.findOne(userId);
		User friend = userRepository.findOne(friendId);

		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else if (friend == null) {
			String msg = String.format("friend not find by id %s", friendId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!friend.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("friend status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else {
			Notification notification = new Notification();
			notification.setFromId(userId);
			notification.setToId(friendId);
			notification.setType(NotificationTypeEnum.FRIEND);
			notificationService.addNotification(notification);
		}
	}

	@Override
	public void deleteFriend(String userId, String friendId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(friendId, "friendId must not be null");

        User user = userRepository.findOne(userId);
        User friend = userRepository.findOne(friendId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (friend == null) {
            String msg = String.format("friend not find by id %s", friendId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!friend.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("friend status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            relationRepository.delete(new RelationCriteria(userId, friendId, TypeRelationEnum.FRIEND));
            accountStoreService.update(userId);
        }
	}

	@Override
	public void addPublisher(String userId, String publisherId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(publisherId, "publisherId must not be null");
		
		User user = userRepository.findOne(userId);
		User publisher = userRepository.findOne(publisherId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else if (publisher == null) {
			String msg = String.format("publisher not find by id %s", publisherId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!publisher.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("publisher status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else {
            relationRepository.save(Relation.getRelation(userId, publisherId, TypeRelationEnum.PUBLISHER));
            accountStoreService.update(userId);
		}
	}

	@Override
	public void deletePublisher(String userId, String publisherId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(publisherId, "publisherId must not be null");
		
		User user = userRepository.findOne(userId);
		User publisher = userRepository.findOne(publisherId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else if (publisher == null) {
			String msg = String.format("publisher not find by id %s", publisherId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!publisher.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("publisher status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else {
            relationRepository.delete(new RelationCriteria(userId, publisherId, TypeRelationEnum.PUBLISHER));
            accountStoreService.update(userId);
		}
	}

	@Override
	public void sendOwnerRequest(String userId, String communityId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(communityId, "communityId must not be null");
		
		User user = userRepository.findOne(userId);
		Community community = communityRepository.findOne(communityId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
		} else if (community == null) {
			String msg = String.format("community not find by id %s", communityId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
		} else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
		} else {
			Notification notification = new Notification();
			notification.setFromId(userId);
			notification.setToId(communityId);
			notification.setType(NotificationTypeEnum.OWNER);
			notificationService.addNotification(notification);
		}
	}

	@Override
	public void deleteOwner(String userId, String communityId) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(communityId, "communityId must not be null");
		
		User user = userRepository.findOne(userId);
		Community community = communityRepository.findOne(communityId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (community == null) {
            String msg = String.format("community not find by id %s", communityId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
            if (relationRepository.countByCriteria(new RelationCriteria(userId, communityId, TypeRelationEnum.OWNER)) == 1) {
                String msg = String.format("the sole owner %s of the user community active %s", userId, communityId);
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_SOLE_OWNER_OF_THE_USER_COMMUNITY_ACTIVE);
            }

            relationRepository.delete(new RelationCriteria(userId, communityId, TypeRelationEnum.OWNER));
            accountStoreService.update(userId);
		}		
	}

	@Override
	public void sendEmployeeRequest(String userId, String communityId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(communityId, "communityId must not be null");

        User user = userRepository.findOne(userId);
        Community community = communityRepository.findOne(communityId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (community == null) {
            String msg = String.format("community not find by id %s", communityId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
			Notification notification = new Notification();
			notification.setFromId(userId);
			notification.setToId(communityId);
			notification.setType(NotificationTypeEnum.EMPLOYEE);
			notificationService.addNotification(notification);
		}
	}

	@Override
	public void deleteEmployee(String userId, String communityId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(communityId, "communityId must not be null");

        User user = userRepository.findOne(userId);
        Community community = communityRepository.findOne(communityId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (community == null) {
            String msg = String.format("community not find by id %s", communityId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
            if (relationRepository.countByCriteria(new RelationCriteria(userId, communityId, TypeRelationEnum.OWNER)) == 1) {
                String msg = String.format("the sole owner %s of the user community active %s", userId, communityId);
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_SOLE_OWNER_OF_THE_USER_COMMUNITY_ACTIVE);
            }

            relationRepository.delete(new RelationCriteria(userId, communityId, TypeRelationEnum.EMPLOYEE));
            accountStoreService.update(userId);
		}
	}

	@Override
	public void sendMemberRequest(String userId, String communityId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(communityId, "communityId must not be null");

        User user = userRepository.findOne(userId);
        Community community = communityRepository.findOne(communityId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (community == null) {
            String msg = String.format("community not find by id %s", communityId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
			Notification notification = new Notification();
			notification.setFromId(userId);
			notification.setToId(communityId);
			notification.setType(NotificationTypeEnum.MEMBER);
			notificationService.addNotification(notification);
		}
	}

	@Override
	public void deleteMember(String userId, String communityId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(communityId, "communityId must not be null");

        User user = userRepository.findOne(userId);
        Community community = communityRepository.findOne(communityId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (community == null) {
            String msg = String.format("community not find by id %s", communityId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_FOUND);
        } else if (!community.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("community status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_COMMUNITY_NOT_ACTIVE);
        } else {
            relationRepository.delete(new RelationCriteria(userId, communityId, TypeRelationEnum.MEMBER));
            accountStoreService.update(userId);
		}
	}
}
