package com.heaptrip.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.account.user.AuthenticationService;
import com.heaptrip.domain.service.system.LocaleService;

@Component("userAuthenticationProvider")
public class AuthenticationProviderImpl implements
		AuthenticationProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(AuthenticationProvider.class);

	@Autowired
	private  AuthenticationService authenticationService;

	@Autowired
	private LocaleService localeService;

	@Autowired(required = false)
	private HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String email = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());

		LOG.info("user " + email + " trying authenticate");

		// Check user authentication info
		User user = authenticationService.getUserByEmailAndPassword(email, password);

		if (user == null) {
			LOG.error("user " + email + " authenticate failure");
			throw new BadCredentialsException(
					localeService.getMessage("err.login.failure"));
		}

		// Return an authenticated token, containing user data and
		// authorities

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		if (user.getRoles() != null) {
			for (String role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public void authenticateInternal(User user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRoles() != null) {
			for (String role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user, null, authorities);
		auth.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
