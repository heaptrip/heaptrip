package com.heaptrip.web.controller.socnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.domain.entity.socnet.fb.FBAccessToken;
import com.heaptrip.domain.entity.socnet.fb.FBUser;
import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.domain.service.socnet.SocnetAuthorizeException;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.adm.RegistrationInfo;

@Controller
public class SocNetController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(SocNetController.class);

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService requestScopeService;

	@Autowired
	private VKontakteAPIService vkontakteAPIService;

	@Autowired
	private FaceBookAPIService faceBookAPIService;

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
	public ModelAndView registrationVKontakte(@RequestParam("access_token") String accessToken,
			@RequestParam("user_id") String userId) {

		ModelAndView mv = new ModelAndView();

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
		registrationInfo.setSocNetName(VKontakteAPIService.SOC_NET_NAME);
		registrationInfo.setSocNetName(vkUser.getUid());

		return mv.addObject("registrationInfo", registrationInfo);

	}

	@RequestMapping(value = "registration/socnet/vk", params = ("error=access_denied"), method = RequestMethod.GET)
	public void registrationVKontakteAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		throw new SocnetAuthorizeException(errorDescription);
	}

	@RequestMapping(value = "registration/socnet/fb", method = RequestMethod.GET)
	public String registrationFaceBookRedirect(@RequestParam("code") String code) {

		FBAccessToken fbAccessToken = new FBAccessToken();

		try {

			fbAccessToken = faceBookAPIService.getAccessTokenByClientCode(code,
					requestScopeService.getCurrentContextPath() + "/rest/registration/socnet/fb");
		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

		StringBuilder url = new StringBuilder(requestScopeService.getCurrentContextPath());

		url.append("/registration.html");
		url.append("?");
		url.append("fb").append("=").append(true);
		url.append("&");
		url.append("access_token").append("=").append(fbAccessToken.getAccess_token());

		return "redirect:" + url.toString();

	}

	@RequestMapping(value = "registration", params = ("fb=true"), method = RequestMethod.GET)
	public ModelAndView registrationFaceBook(@RequestParam("access_token") String accessToken) {

		ModelAndView mv = new ModelAndView();

		RegistrationInfo registrationInfo = new RegistrationInfo();

		FBAccessToken fbAccessToken = new FBAccessToken();

		fbAccessToken.setAccess_token(accessToken);

		FBUser user = null;

		try {
			user = faceBookAPIService.getUser(fbAccessToken);
		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

		registrationInfo.setFirstName(user.getFirst_name());
		registrationInfo.setSecondName(user.getLast_name());
		registrationInfo.setPhotoUrl(user.getPicture_large());
		registrationInfo.setSocNetName(FaceBookAPIService.SOC_NET_NAME);
		registrationInfo.setSocNetUserUID(user.getId());

		mv.addObject("registrationInfo", registrationInfo);

		return mv;

	}

	@RequestMapping(value = "registration/socnet/fb", params = ("error=access_denied"), method = RequestMethod.GET)
	public void registrationFaceBookAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		throw new SocnetAuthorizeException(errorDescription);
	}

	@ExceptionHandler(SocnetAuthorizeException.class)
	public String handleSocnetAuthorizeException(SocnetAuthorizeException e) {
		LOG.error("Social network authorize error", e);
		return "redirect:login.html";
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public ModelAndView emptyRegistration() {
		RegistrationInfo info = new RegistrationInfo();
		return new ModelAndView().addObject("registrationInfo", info);
	}

}
