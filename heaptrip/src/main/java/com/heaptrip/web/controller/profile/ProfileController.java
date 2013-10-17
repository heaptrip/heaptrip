package com.heaptrip.web.controller.profile;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.profile.AccountModelInfo;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.modelservice.ProfileModelService;

@Controller
public class ProfileController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileModelService profileModelService;

	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public ModelAndView getProfileInformation(@RequestParam String uid) {

		ModelAndView mv = new ModelAndView();

		AccountModelInfo accountModel = profileModelService.getProfileInformation(uid);

		return mv.addObject("account", accountModel);
	}

	@RequestMapping(value = "profile_modify_info", method = RequestMethod.GET)
	public ModelAndView getEditTripInfo(@RequestParam(value = "uid", required = false) String accountId) {
		/*ModelAndView mv = new ModelAndView();
		AccountModel accountId = null;
		if (accountId != null) {
			
				tripModel = tripModelService.getTripInfoById(tripId, scopeService.getCurrentLocale(), false);
			
		}
		return mv.addObject("account", tripModel);*/
		return null;
	}
	
}
