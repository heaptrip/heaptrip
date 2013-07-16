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

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.security.AuthenticationProvider;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.converter.UserModelService;
import com.heaptrip.web.model.user.RegistrationInfoModel;

@Controller
public class UserController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService scopeService;

	@Autowired
	private UserModelService userModelService;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> registration(
			@RequestBody RegistrationInfoModel registrationInfo) {

		LOG.info(registrationInfo.toString());

		try {

			User user = userModelService.registration(registrationInfo);

			if (user == null)
				throw scopeService.getErrorServise().createBusinessExeption(
						ErrorEnum.REGISTRATION_FAILURE);

			authenticationProvider.authenticateInternal(user);

		} catch (Throwable e) {
			throw new RestException(e);
		}

		return Ajax.emptyResponse();
	}

}
