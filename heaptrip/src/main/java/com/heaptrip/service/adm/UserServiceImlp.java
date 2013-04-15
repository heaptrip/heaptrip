package com.heaptrip.service.adm;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.domain.service.adm.UserService;

@Service
public class UserServiceImlp implements UserService {

	@Override
	public User getUserByAuthenticationInfo(String username, String password) {

		User user = null;

		if (username.equals("user") && password.equals("user")) {
			user = new User();
			user.setFirstName("Иван");
			user.setSecondName("Иванов");
			String[] roles = { "ROLE_USER" };
			user.setRoles(roles);
		}

		return user;
	}

	@Override
	public User getCurrentUser() {
		User result = null;
		try {
			result = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Throwable e) {
		}
		return result;
	}

}
