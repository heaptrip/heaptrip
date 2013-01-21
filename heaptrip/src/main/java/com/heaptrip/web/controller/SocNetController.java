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
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class SocNetController {

	@Autowired
	RequestScopeService sessionScope;

	@Autowired
	VKontakteAPIService vkontakteAPIService;

	private static final Logger LOG = LoggerFactory.getLogger(SocNetController.class);

	@RequestMapping(value = "registration/socnet/vk", method = RequestMethod.GET)
	public @ModelAttribute("registrationInfo")
	RegistrationInfo registrationVKontakte(@RequestParam("code") String code) {

		RegistrationInfo registrationInfo = new RegistrationInfo();

		VKAccessToken vkAccessToken = vkontakteAPIService.getAccessTokenByClientCode(code,
				sessionScope.getCurrentUrl(false));

		VKUser vkUser = vkontakteAPIService.getUser(vkAccessToken);

		registrationInfo.setFirstName(vkUser.getFirst_name());
		registrationInfo.setSecondName(vkUser.getLast_name());
		registrationInfo.setPhotoUrl(vkUser.getPhoto_medium());

		return registrationInfo;

	}

	@RequestMapping(value = "registration/socnet/vk", params = ("error=access_denied"), method = RequestMethod.GET)
	public String registrationVKontakteAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		return "redirect:" + sessionScope.getCurrentContextPath() + "/login.html";
	}

}
