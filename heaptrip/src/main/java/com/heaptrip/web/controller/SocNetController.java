package com.heaptrip.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.domain.service.socnet.SocnetAuthorizeException;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class SocNetController {

	@Autowired
	RequestScopeService requestScopeService;

	@Autowired
	VKontakteAPIService vkontakteAPIService;

	private static final Logger LOG = LoggerFactory.getLogger(SocNetController.class);

	@RequestMapping(value = "registration/socnet/vk", method = RequestMethod.GET)
	public String registrationVKontakteRedirect(@RequestParam("code") String code) {

		VKAccessToken vkAccessToken = null;

		try {
			vkAccessToken = vkontakteAPIService.getAccessTokenByClientCode(code,
					requestScopeService.getCurrentContextPath() + "/rest/registration/socnet/vk");
		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

		StringBuilder url = new StringBuilder(requestScopeService.getCurrentContextPath());

		url.append("/registration.html");
		url.append("?");
		url.append("vk").append("=").append(true);
		url.append("&");
		url.append("access_token").append("=").append(vkAccessToken.getAccess_token());
		url.append("&");
		url.append("user_id").append("=").append(vkAccessToken.getUser_id());

		return "redirect:" + url.toString();

	}

	@RequestMapping(value = "registration", params = ("vk=true"), method = RequestMethod.GET)
	public @ModelAttribute("registrationInfo")
	RegistrationInfo registrationVKontakte(@RequestParam("access_token") String accessToken,
			@RequestParam("user_id") String userId) {

		RegistrationInfo registrationInfo = new RegistrationInfo();

		VKAccessToken vkAccessToken = new VKAccessToken();
		vkAccessToken.setAccess_token(accessToken);
		vkAccessToken.setUser_id(userId);

		VKUser vkUser = null;

		try {
			vkUser = vkontakteAPIService.getUser(vkAccessToken);
		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

		registrationInfo.setFirstName(vkUser.getFirst_name());
		registrationInfo.setSecondName(vkUser.getLast_name());
		registrationInfo.setPhotoUrl(vkUser.getPhoto_big());

		return registrationInfo;

	}

	@RequestMapping(value = "registration/socnet/vk", params = ("error=access_denied"), method = RequestMethod.GET)
	public void registrationVKontakteAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		throw new SocnetAuthorizeException(errorDescription);
	}

	
	@RequestMapping(value = "registration/socnet/fb", method = RequestMethod.GET)
	public String registrationFaceBookRedirect(@RequestParam("code") String code) {

		VKAccessToken vkAccessToken = null;

		try {
			vkAccessToken = vkontakteAPIService.getAccessTokenByClientCode(code,
					requestScopeService.getCurrentContextPath() + "/rest/registration/socnet/vk");
		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

		StringBuilder url = new StringBuilder(requestScopeService.getCurrentContextPath());

		url.append("/registration.html");
		url.append("?");
		url.append("vk").append("=").append(true);
		url.append("&");
		url.append("access_token").append("=").append(vkAccessToken.getAccess_token());
		url.append("&");
		url.append("user_id").append("=").append(vkAccessToken.getUser_id());

		return "redirect:" + url.toString();

	}

	
	
	@ExceptionHandler(SocnetAuthorizeException.class)
	public String handleIOException(SocnetAuthorizeException e) {
		LOG.error("Social network authorize error", e);
		return "redirect:" + requestScopeService.getCurrentContextPath() + "/login.html";
	}

}
