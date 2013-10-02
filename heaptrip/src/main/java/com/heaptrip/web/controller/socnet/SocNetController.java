package com.heaptrip.web.controller.socnet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.socnet.fb.FBAccessToken;
import com.heaptrip.domain.entity.socnet.fb.FBUser;
import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;
import com.heaptrip.domain.service.account.user.AuthenticationService;
import com.heaptrip.domain.service.socnet.SocnetAuthorizeException;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.security.AuthenticationProvider;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.user.RegistrationInfoModel;

@Controller
public class SocNetController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(SocNetController.class);

	@Autowired
	private VKontakteAPIService vkontakteAPIService;

	@Autowired
	private FaceBookAPIService faceBookAPIService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	/**
	 * Метод вызывается редиректом из соц. сети в случае успешного ввода логина
	 * и пароля соц. сети.
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "registration/socnet/vk", method = RequestMethod.GET)
	public String registrationVKontakteRedirect(@RequestParam("code") String code) {

		try {

			VKAccessToken vkAccessToken = vkontakteAPIService.getAccessTokenByClientCode(code,
					scopeService.getCurrentContextPath() + "/rest/registration/socnet/vk");

			VKUser vkUser = vkontakteAPIService.getUser(vkAccessToken);

			User user = authenticationService.getUserBySocNetUID(VKontakteAPIService.SOC_NET_NAME, vkUser.getUid(),
					new HttpClient().doInputStreamGet(vkUser.getPhoto_big()));

			if (user == null) {
				// если пользователя нет, на страницу регистрации
				StringBuilder url = new StringBuilder(scopeService.getCurrentContextPath());
				url.append("/registration.html");
				url.append("?");
				url.append("snn").append("=").append(VKontakteAPIService.SOC_NET_NAME);
				url.append("&");
				url.append("access_token").append("=").append(vkAccessToken.getAccess_token());
				url.append("&");
				url.append("user_id").append("=").append(vkAccessToken.getUser_id());
				return "redirect:" + url.toString();
			} else {
				if (user.getStatus().equals(AccountStatusEnum.NOTCONFIRMED)) {
					// если пользователь есть но не подтвердил регистрацию, на
					// страницу с сылкой на почту
					return "redirect:" + scopeService.getCurrentContextPath() + "/confirmation.html?domain="
							+ user.getEmail().substring(user.getEmail().indexOf("@"));
				} else {
					// если пользователь есть регистрация подтверждена,
					// логинемся
					authenticationProvider.authenticateInternal(user);
					return "redirect:" + scopeService.getCurrentContextPath() + "/";
				}
			}

		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

	}

	@RequestMapping(value = "registration", params = ("snn=" + VKontakteAPIService.SOC_NET_NAME), method = RequestMethod.GET)
	public ModelAndView registrationVKontakte(@RequestParam("access_token") String accessToken,
			@RequestParam("user_id") String userId) {

		try {

			ModelAndView mv = new ModelAndView();

			RegistrationInfoModel registrationInfo = new RegistrationInfoModel();

			VKAccessToken vkAccessToken = new VKAccessToken();
			vkAccessToken.setAccess_token(accessToken);
			vkAccessToken.setUser_id(userId);

			VKUser vkUser = vkontakteAPIService.getUser(vkAccessToken);

			registrationInfo.setFirstName(vkUser.getFirst_name());
			registrationInfo.setSecondName(vkUser.getLast_name());
			registrationInfo.setPhotoUrl(vkUser.getPhoto_big());
			registrationInfo.setSocNetName(VKontakteAPIService.SOC_NET_NAME);
			registrationInfo.setSocNetUserUID(vkUser.getUid());

			return mv.addObject("registrationInfo", registrationInfo);

		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

	}

	@RequestMapping(value = "registration/socnet/vk", params = ("error=access_denied"), method = RequestMethod.GET)
	public void registrationVKontakteAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		throw new SocnetAuthorizeException(errorDescription);
	}

	@RequestMapping(value = "registration/socnet/fb", method = RequestMethod.GET)
	public String registrationFaceBookRedirect(@RequestParam("code") String code) {

		try {

			FBAccessToken fbAccessToken = faceBookAPIService.getAccessTokenByClientCode(code,
					scopeService.getCurrentContextPath() + "/rest/registration/socnet/fb");

			StringBuilder url = new StringBuilder(scopeService.getCurrentContextPath());

			url.append("/registration.html");
			url.append("?");
			url.append("snn").append("=").append(FaceBookAPIService.SOC_NET_NAME);
			url.append("&");
			url.append("access_token").append("=").append(fbAccessToken.getAccess_token());

			return "redirect:" + url.toString();

		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

	}

	@RequestMapping(value = "registration", params = ("snn=" + FaceBookAPIService.SOC_NET_NAME), method = RequestMethod.GET)
	public ModelAndView registrationFaceBook(@RequestParam("access_token") String accessToken) {
		try {
			ModelAndView mv = new ModelAndView();

			RegistrationInfoModel registrationInfo = new RegistrationInfoModel();

			FBAccessToken fbAccessToken = new FBAccessToken();
			fbAccessToken.setAccess_token(accessToken);

			FBUser user = faceBookAPIService.getUser(fbAccessToken);

			registrationInfo.setFirstName(user.getFirst_name());
			registrationInfo.setSecondName(user.getLast_name());
			registrationInfo.setPhotoUrl(user.getPicture_large());
			registrationInfo.setSocNetName(FaceBookAPIService.SOC_NET_NAME);
			registrationInfo.setSocNetUserUID(user.getId());

			mv.addObject("registrationInfo", registrationInfo);

			return mv;

		} catch (Exception e) {
			throw new SocnetAuthorizeException(e.getMessage());
		}

	}

	@RequestMapping(value = "registration/socnet/fb", params = ("error=access_denied"), method = RequestMethod.GET)
	public void registrationFaceBookAccessDeniedProcess(@RequestParam("error_reason") String errorReason,
			@RequestParam("error_description") String errorDescription) {
		throw new SocnetAuthorizeException(errorDescription);
	}

	@ExceptionHandler(SocnetAuthorizeException.class)
	public String handleSocnetAuthorizeException(SocnetAuthorizeException e) {
		try {
			LOG.error("Social network authorize error", e);
			return "redirect:" + scopeService.getCurrentContextPath() + "/login.html?login_error="
					+ URLEncoder.encode(e.getLocalizedMessage(), "UTF-8");
		} catch (UnsupportedEncodingException exp) {
			throw new RuntimeException(e);
		}
	}

}
