package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.UserInfoModel;
import com.heaptrip.web.modelservice.ProfileModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ProfileController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    ProfileModelService profileModelService;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView getProfileInformation(@RequestParam(required = false) String guid) {
        ModelAndView mv = new ModelAndView();
        if (guid == null) {
            User currentUser = scopeService.getCurrentUser();
            if (currentUser != null) {
                guid = currentUser.getId();
            }
        } else {
            mv.addObject("owner", profileModelService.getAccountInformation(guid));
        }
        UserInfoModel accountModel = profileModelService.getProfileInformation(guid);


        return mv.addObject("account", accountModel);
    }

    @RequestMapping(value = "profile_modify_info", method = RequestMethod.GET)
    public ModelAndView getEditTripInfo() {
        ModelAndView mv = new ModelAndView();
        UserInfoModel accountModel = null;
        String uid = null;
        User currentUser = scopeService.getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getId();
        }

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
