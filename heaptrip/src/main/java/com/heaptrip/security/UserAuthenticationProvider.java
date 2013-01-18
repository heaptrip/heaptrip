package com.heaptrip.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.domain.service.adm.LocaleService;
import com.heaptrip.domain.service.adm.UserService;

@Component("userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationProvider.class);

	@Autowired
	private UserService userService;

	@Autowired
	private LocaleService localeService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		LOG.info("user " + username + " trying authenticate");

		// Check user authentication info
		User user = userService.getUserByAuthenticationInfo(username, password);

		if (user == null) {
			LOG.error("user " + username + " authenticate failure");
			throw new BadCredentialsException(localeService.getMessage("err.login.failure"));
		}

		// Preferably clear the password in the user object before storing in
		// authentication object
		user.clearPassword();

		// Return an authenticated token, containing user data and
		// authorities

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
