package com.heaptrip.web.controller.profile;

import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.UserInfoModel;
import com.heaptrip.web.model.travel.TripInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.modelservice.ProfileModelService;

import java.util.Map;

@Controller
public class ProfileController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    ProfileModelService profileModelService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView getProfileInformation(@RequestParam String uid) {
        ModelAndView mv = new ModelAndView();
        UserInfoModel accountModel = profileModelService.getProfileInformation(uid);
        return mv.addObject("account", accountModel);
    }

    @RequestMapping(value = "profile_modify_info", method = RequestMethod.GET)
    public ModelAndView getEditTripInfo(@RequestParam(required = false) String uid) {
        ModelAndView mv = new ModelAndView();
        UserInfoModel accountModel = null;
        if (uid != null) {
            accountModel = profileModelService.getProfileInformation(uid);
        }
        return mv.addObject("account", accountModel);
    }


    @RequestMapping(value = "security/account_update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateUserInfo(@RequestBody UserInfoModel userInfoModel) {
        try {
            profileModelService.updateProfileInfo(userInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

}
