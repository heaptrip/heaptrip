package com.heaptrip.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.Setting;
import com.heaptrip.domain.repository.user.UserSettingRepository;
import com.heaptrip.domain.service.user.UserSettingService;

@Service
public class UserSettingServiceImpl implements UserSettingService {

	@Autowired
	private UserSettingRepository userSettingRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserSettingServiceImpl.class);
	
	@Override
	public void saveSetting(String userId, Setting userSetting) {
		Assert.notNull(userId, "user id must not be null");
		Assert.notNull(userSetting, "userSetting must not be null");
		
		User user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user not find by userId: %s", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			userSettingRepository.saveSetting(userId, userSetting);
		}
	}

	@Override
	public void profileImageFrom(String userId, SocialNetworkEnum socialNetwork) {
		Assert.notNull(userId, "userId must not be null");
		User user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user not find by userId: %s", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			boolean findNet = false;
			
			if (socialNetwork.equals(SocialNetworkEnum.NONE)) {
				findNet = true;
			} else if (user.getNet() != null) {
				for (SocialNetwork net : user.getNet()) {
					net.getId().equals(socialNetwork);
					findNet = true;
					break;
				}
			}
			
			if (findNet) {
				userSettingRepository.profileImageFrom(userId, socialNetwork);
			} else {
				throw new IllegalArgumentException(String.format("user id=%s don`t have social net ", userId, socialNetwork));
			}
		}
	}
	
	@Override
	public void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork) {
		Assert.notNull(userId, "userId must not be null");
		Assert.isTrue(!socialNetwork.equals(SocialNetworkEnum.NONE), "socialNetwork must not be NONE");
		User user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user with id=%s is not found", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			SocialNetwork unlinkNet = null;
			
			if (user.getNet() != null) {
				if (user.getNet().length == 1 && userSettingRepository.isEmptyPassword(userId)) {
					String msg = String.format("user id=%s have one social network and empty password", userId);
					logger.debug(msg);
					// TODO dikma: создать бизнес исключение
					throw new IllegalArgumentException(msg);
				}
				
				for (SocialNetwork net : user.getNet()) {
					if (net.getId().equals(socialNetwork.toString())) {
						unlinkNet = net;
						break;
					}
				}
			}
			
			if (unlinkNet == null) {
				String msg = String.format("user id=%s don`t have social net %s", userId, socialNetwork.toString());
				logger.debug(msg);
				// TODO dikma: создать бизнес исключение
				throw new IllegalArgumentException(msg);
			} else {
				userSettingRepository.unlinkSocialNetwork(userId, socialNetwork);
			}
		}
	}
	
	@Override
	public void deleteUser(String userId) {
		Assert.notNull(userId, "userId must not be null");
		// TODO dikma после реализации групп, добавть проверку их наличия, если есть, удалить профиль нельзя если пользователь единственный владелец 
		
		User user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user with id=%s is not found", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			userSettingRepository.deleteUser(userId);
		}
	}

	@Override
	public void linkSocialNetwork(String userId, SocialNetwork socialNetwork) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(socialNetwork, "socialNetwork must not be null");
		Assert.notNull(socialNetwork.getId(), "socialNetwork id must not be null");
		Assert.notNull(socialNetwork.getUid(), "socialNetwork uid must not be null");
		
		User user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user with id=%s is not found", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			boolean existsNet = false;
			
			if (user.getNet() != null) {
				for (SocialNetwork net : user.getNet()) {
					if (net.getId().equals(socialNetwork.getId())) {
						existsNet = true;
						break;
					}
				}
			}
			
			if (existsNet) {
				String msg = String.format("user id=%s have social net %s", userId, socialNetwork.toString());
				logger.debug(msg);
				// TODO dikma: создать бизнес исключение
				throw new IllegalArgumentException(msg);
			} else {
				userSettingRepository.linkSocialNetwork(userId, socialNetwork);
			}
		}
	}
}
