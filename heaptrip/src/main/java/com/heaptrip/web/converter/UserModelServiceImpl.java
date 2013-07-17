package com.heaptrip.web.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.domain.service.user.AuthenticationService;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.web.model.user.RegistrationInfoModel;

@Service
public class UserModelServiceImpl implements UserModelService {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public User registration(RegistrationInfoModel regInfo) {

		UserRegistration userReg = new UserRegistration();

		String[] roles = { "ROLE_USER" };
		InputStream photo = null;

		userReg.setEmail(regInfo.getEmail());
		userReg.setFirstName(regInfo.getFirstName());
		userReg.setSecondName(regInfo.getSecondName());
		userReg.setPassword(regInfo.getPassword());
		userReg.setRoles(roles);

		if (regInfo.getSocNetName() != null
				&& regInfo.getSocNetUserUID() != null) {

			if (regInfo.getPhotoUrl() != null) {
				byte[] photoByUrl = new HttpClient().doByteGet(regInfo
						.getPhotoUrl());
				if (photoByUrl != null)
					photo = new ByteArrayInputStream(photoByUrl);
			}

			userReg.setNet(new SocialNetwork[] { new SocialNetwork(
					procsessSocNetName(regInfo.getSocNetName()), regInfo
							.getSocNetUserUID(), photo != null) });

		}

		User user = null;

		try {
			user = authenticationService.registration(userReg, photo);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	private SocialNetworkEnum procsessSocNetName(String socNetName) {
		SocialNetworkEnum result = null;
		if (socNetName.equals(FaceBookAPIService.SOC_NET_NAME)) {
			result = SocialNetworkEnum.FB;
		} else if (socNetName.equals(VKontakteAPIService.SOC_NET_NAME)) {
			result = SocialNetworkEnum.VK;
		}
		return result;
	}

}
