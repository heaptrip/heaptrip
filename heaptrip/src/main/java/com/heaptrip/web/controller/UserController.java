package com.heaptrip.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class UserController {

	@Autowired
	VKontakteAPIService vkontakteAPIService;

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public @ModelAttribute("registrationInfo")
	RegistrationInfo registrationVKontakte(@RequestParam("code") String code) {

		RegistrationInfo registrationInfo = new RegistrationInfo();

		VKAccessToken vkAccessToken = vkontakteAPIService.getAccessTokenByClientCode(code,
				"http://localhost:8080/heaptrip/registration.html");

		LOG.info("TRACE ------- " + vkAccessToken.getAccess_token());

		VKUser vkUser = vkontakteAPIService.getUser(vkAccessToken);

		registrationInfo.setFirstName(vkUser.getFirst_name());
		registrationInfo.setSecondName(vkUser.getLast_name());

		return registrationInfo;

	}
}
