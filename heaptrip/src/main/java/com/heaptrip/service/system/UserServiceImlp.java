package com.heaptrip.service.system;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.UserService;

@Service
public class UserServiceImlp implements UserService {

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
