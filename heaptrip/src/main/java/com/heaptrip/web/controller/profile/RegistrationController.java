package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.account.AccountService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.CommunityInfoModel;
import com.heaptrip.web.model.profile.RegistrationInfoModel;
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
public class RegistrationController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    ProfileModelService profileModelService;


    @RequestMapping(value = "user/registration", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> userRegistration(@RequestBody RegistrationInfoModel registrationInfo) {
        try {
            User user = profileModelService.registration(registrationInfo);
            if (user == null)
                throw scopeService.getErrorServise().createException(AccountException.class,
                        ErrorEnum.REGISTRATION_FAILURE);
        } catch (Throwable e) {
            throw new RestException(e);
        }

        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/community/registration", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> communityRegistration(@RequestBody CommunityInfoModel communityInfoModel) {
        try {
            Community community = profileModelService.registration(communityInfoModel);
            if (community == null)
                throw scopeService.getErrorServise().createException(AccountException.class,
                        ErrorEnum.REGISTRATION_FAILURE);
        } catch (Throwable e) {
            throw new RestException(e);
        }

        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public ModelAndView emptyRegistration() {
        RegistrationInfoModel info = new RegistrationInfoModel();
        return new ModelAndView().addObject("registrationInfo", info);
    }

    @RequestMapping(value = "registration/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam String uid, @RequestParam String value) {
        accountService.confirmRegistration(uid, value);
        // authenticationProvider.authenticateInternal(user);
        return "redirect:" + scopeService.getCurrentContextPath() + "/pf/login";
    }

}
