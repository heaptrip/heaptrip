package com.heaptrip.security;

import com.heaptrip.domain.entity.account.user.User;

public interface AuthenticationProvider extends
		org.springframework.security.authentication.AuthenticationProvider {

	void authenticateInternal(User user);

}
