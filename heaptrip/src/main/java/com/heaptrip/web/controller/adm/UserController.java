package com.heaptrip.web.controller.adm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.domain.service.user.OldAuthenticationService;
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
	private OldAuthenticationService authenticationService;

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
				throw scopeService.getErrorServise().createException(AccountException.class,
						ErrorEnum.REGISTRATION_FAILURE);

		} catch (Throwable e) {
			throw new RestException(e);
		}

		return Ajax.emptyResponse();
	}

	@RequestMapping(value = "registration/confirm", method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam String uid) {

		// TODO : authenticationService.confirmRegistration(uid);

		// TODO : authenticationProvider.authenticateInternal(user);

		LOG.info("Call MAIL LINK registration/confirm?uid=" + uid);

		return "redirect:" + scopeService.getCurrentContextPath()
				+ "/travels.html";
	}

}
