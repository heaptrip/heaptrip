package com.heaptrip.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.repository.user.UserSettingRepository;
import com.heaptrip.domain.service.user.UserSettingService;

@Service
public class UserSettingServiceImpl implements UserSettingService {

	@Autowired
	private UserSettingRepository userSettingRepository;
	
	@Override
	public void saveSetting(UserSetting userSetting) {
		userSettingRepository.saveSetting(userSetting);
	}

	@Override
	public void deleteUserProfile(String userId) {
		Assert.notNull(userId, "userId must not be null");
		// TODO dikma после реализации групп, добавть проверку их наличия, если есть, удалить профиль нельзя если пользователь единственный владелец 
		userSettingRepository.deleteUserProfile(userId);
	}

	@Override
	public void profileImageFrom(String userId, String socialNetworkName) {
		Assert.notNull(userId, "userId must not be null");
		UserSetting user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			throw new IllegalArgumentException(String.format("user with id=%s is not found", userId));
		} else {
			
			boolean findNet = false;
			
			for (SocialNetwork net : user.getNet()) {
				net.getId().equals(socialNetworkName);
				findNet = true;
				break;
			}
			
			if (findNet) {
				userSettingRepository.profileImageFrom(userId, socialNetworkName);
			} else {
				throw new IllegalArgumentException(String.format("user id=%s don`t have social net ", userId, socialNetworkName));
			}
		}
	}

	@Override
	public void unlinkSocialNetwork(String userId, String socialNetworkName) {
		Assert.notNull(userId, "userId must not be null");
		UserSetting user = userSettingRepository.findOne(userId);
		
		if (user == null) {
			throw new IllegalArgumentException(String.format("user with id=%s is not found", userId));
		} else {
			if (user.getNet().length == 1 && userSettingRepository.isEmptyPassword(userId)) {
				throw new IllegalArgumentException(String.format("user id=%s have one social network and empty password", userId));
			}
			
			boolean findNet = false;
			
			for (SocialNetwork net : user.getNet()) {
				net.getId().equals(socialNetworkName);
				findNet = true;
				break;
			}
			
			if (findNet) {
				userSettingRepository.unlinkSocialNetwork(userId, socialNetworkName);
			} else {
				throw new IllegalArgumentException(String.format("user id=%s don`t have social net ", userId, socialNetworkName));
			}
		}
			
	}
}
