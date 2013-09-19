package com.heaptrip.web.modelservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.domain.service.system.LocaleService;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.web.model.user.RegistrationInfoModel;

@Service
public class UserModelServiceImpl implements UserModelService {

	@Autowired
	private UserService userService;

	@Autowired
	private LocaleService localeService;

	@Override
	public User registration(RegistrationInfoModel regInfo) {

		UserRegistration userReg = new UserRegistration();

		String[] roles = { "ROLE_USER" };
		InputStream photo = null;

		userReg.setEmail(regInfo.getEmail());
		// TODO: Дима теперь у пользователя просто имя ;).
		// TODO: А как же форма регистрации, надо дизайн менять :)
		userReg.setName(regInfo.getFirstName() + " " + regInfo.getSecondName());
		userReg.setPassword(regInfo.getPassword());
		userReg.setRoles(roles);

		if (regInfo.getSocNetName() != null && !regInfo.getSocNetName().isEmpty() && regInfo.getSocNetUserUID() != null
				&& !regInfo.getSocNetUserUID().isEmpty()) {

			if (regInfo.getPhotoUrl() != null && !regInfo.getPhotoUrl().isEmpty()) {
				byte[] photoByUrl = new HttpClient().doByteGet(regInfo.getPhotoUrl());
				if (photoByUrl != null)
					photo = new ByteArrayInputStream(photoByUrl);
			}

			userReg.setNet(new SocialNetwork[] { new SocialNetwork(procsessSocNetName(regInfo.getSocNetName()), regInfo
					.getSocNetUserUID()) });

		}

		User user = null;

		try {
			try {
				user = userService.registration(userReg, photo, localeService.getCurrentLocale());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
