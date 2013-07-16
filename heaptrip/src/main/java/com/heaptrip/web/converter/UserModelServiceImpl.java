package com.heaptrip.web.converter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.service.user.AuthenticationService;
import com.heaptrip.web.model.user.RegistrationInfoModel;

@Service
public class UserModelServiceImpl implements UserModelService {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public User registration(RegistrationInfoModel registrationInfo) {

		UserRegistration userRegistration = new UserRegistration();

		userRegistration.setEmail(registrationInfo.getEmail());
		userRegistration.setFirstName(registrationInfo.getFirstName());
		userRegistration.setSecondName(registrationInfo.getSecondName());
		userRegistration.setPassword(registrationInfo.getPassword());
		// userRegistration.setNet(collectionUregistrationInfo.getSocNetName());

		

		
		User user = null;
		try {
			user = authenticationService.registration(userRegistration, null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String[] roles = { "ROLE_USER" };
		 user.setRoles(roles);
		 
		// TODO Auto-generated method stub
		return user;
	}

}
