package com.heaptrip.web.controller.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class UserController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService scopeService;

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> registrationTest(@RequestBody RegistrationInfo registrationInfo) {

		LOG.info(registrationInfo.toString());

		try {

			if (registrationInfo.getEmail() == null || registrationInfo.getEmail().indexOf("@") < 0)
				throw scopeService.getErrorServise().createBusinessExeption(ErrorEnum.REGISTRATION_FAILURE);

			User user = new User();
			user.setFirstName(registrationInfo.getFirstName());
			user.setSecondName(registrationInfo.getSecondName());
			String[] roles = { "ROLE_USER" };
			user.setRoles(roles);

			authenticateInternal(user);

		} catch (Throwable e) {
			throw new RestException(e);
		}

		return Ajax.emptyResponse();
	}

	public void authenticateInternal(User user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRoles() != null) {
			for (String role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));

			}
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
		auth.setDetails(new WebAuthenticationDetails(scopeService.getCurrentRequest()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

}
