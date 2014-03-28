package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.AccountModel;
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

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProfileController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    ProfileModelService profileModelService;


    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;


    @RequestMapping(value = "communities", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getCommunitiesByCriteria(@RequestBody AccountTextCriteria accountTextCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            result.put("accounts", profileModelService.getAccountsModelByCriteria(accountTextCriteria));
            result.put("count", 100);
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }

    }


    @RequestMapping(value = "*community", method = RequestMethod.GET)
    public ModelAndView getCommunityInformation(@RequestParam(required = false) String guid) {
        ModelAndView mv = new ModelAndView();
        CommunityInfoModel accountModel = profileModelService.getCommunityInformation(guid);
        return mv.addObject("account", accountModel);
    }

    @RequestMapping(value = "*profile", method = RequestMethod.GET)
    public ModelAndView getProfileInformation(@RequestParam(required = false) String guid) {
        ModelAndView mv = new ModelAndView();
        guid = guid != null && guid.isEmpty() ? null : guid;
        if (guid == null) {
            User currentUser = scopeService.getCurrentUser();
            if (currentUser != null) {
                guid = currentUser.getId();
            }
        }
        UserInfoModel accountModel = profileModelService.getUserInformation(guid);
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
        }
        UserInfoModel accountModel = profileModelService.getUserInformation(guid);
        return mv.addObject("account", accountModel);
    }


    @RequestMapping(value = "security/change_image", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateUserInfo(@RequestBody Map<String, String> map) {
        try {
            String accountId = map.get("accountId");
            String imageId = map.get("imageId");
            profileModelService.changeImage(accountId, imageId);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
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



    @RequestMapping(value = "community_modify_info", method = RequestMethod.GET)
    public ModelAndView getEditCommunityInfo(@RequestParam(required = false) String guid) {
        ModelAndView mv = new ModelAndView();
        AccountModel accountModel = null;
        if (guid != null) {
            accountModel = profileModelService.getCommunityInformation(guid);
        }
        return mv.addObject("account",accountModel);
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
