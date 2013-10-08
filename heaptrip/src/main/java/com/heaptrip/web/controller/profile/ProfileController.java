package com.heaptrip.web.controller.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.modelservice.ProfileModelService;

@Controller
public class ProfileController extends ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileModelService profileModelService;

	@RequestMapping(value = "profile", method = RequestMethod.GET)
	public ModelAndView getProfileInformation(@RequestParam String uid) {

		ModelAndView mv = new ModelAndView();

		AccountModel accountModel = profileModelService.getProfileInformation(uid);

		return mv.addObject("account", accountModel);
	}

}
