package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.CommunityInfoModel;
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
        guid = guid != null && guid.isEmpty() ? null : guid;
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
    public ModelAndView getEditProfileInfo(@RequestParam(required = false) String guid) {
        ModelAndView mv = new ModelAndView();
        guid = guid != null && guid.isEmpty() ? null : guid;
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


    @RequestMapping(value = "security/account_update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateUserInfo(@RequestBody UserInfoModel userInfoModel) {
        try {
            profileModelService.updateUserInfo(userInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }


    @RequestMapping(value = "communities", method = RequestMethod.GET)
    public ModelAndView getCommunitiesList(@RequestParam(required = false) String guid) {
        guid = guid != null && guid.isEmpty() ? null : guid;
        ModelAndView mv = new ModelAndView();
        if (guid == null) {
            User currentUser = scopeService.getCurrentUser();
            if (currentUser != null) {
                guid = currentUser.getId();
            }
        } else {
            mv.addObject("owner", profileModelService.getAccountInformation(guid));
        }
        return mv;
    }


    @RequestMapping(value = "community_modify_info", method = RequestMethod.GET)
    public ModelAndView getEditCommunityInfo(@RequestParam(required = false) String guid, @RequestParam(required = false) String id) {
        ModelAndView mv = new ModelAndView();
        guid = guid != null && guid.isEmpty() ? null : guid;
        if (guid == null) {
            User currentUser = scopeService.getCurrentUser();
            if (currentUser != null) {
                guid = currentUser.getId();
            }
        } else {
            mv.addObject("owner", profileModelService.getAccountInformation(guid));
        }
        CommunityInfoModel communityModel = profileModelService.getCommunityInformation(id);
        return mv.addObject("community", communityModel);
    }

    @RequestMapping(value = "security/community_save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> saveCommunityInfo(@RequestBody CommunityInfoModel communityInfoModel) {
        try {
            profileModelService.saveCommunityInfo(communityInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }


    @RequestMapping(value = "security/community_update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateCommunityInfo(@RequestBody CommunityInfoModel communityInfoModel) {
        try {
            profileModelService.updateCommunityInfo(communityInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }


}
