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
import com.heaptrip.web.model.profile.criteria.AccountTextCriteriaMap;
import com.heaptrip.web.modelservice.ProfileModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController extends ExceptionHandlerControler {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileModelService profileModelService;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;

    @RequestMapping(value = "find_communities", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getCommunitiesByCriteria(@RequestBody AccountTextCriteriaMap accountCriteriaMap) {
        try {

            Map<String, Object> result = new HashMap();

            AccountTextCriteria userCommunitiesCriteria = accountCriteriaMap.get("userCommunitiesCriteria");
            if (userCommunitiesCriteria != null) {
                Map<String, Object> userCommunities = new HashMap<>();
                List<AccountModel> communities = profileModelService.getAccountsModelByCriteria(userCommunitiesCriteria);
                userCommunities.put("communities", communities);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                userCommunities.put("count", communities.size());
                result.put("userCommunities", userCommunities);
            }
            AccountTextCriteria employerCommunitiesCriteria = accountCriteriaMap.get("employerCommunitiesCriteria");
            if (employerCommunitiesCriteria != null) {
                Map<String, Object> employerCommunities = new HashMap<>();
                List<AccountModel> communities = profileModelService.getAccountsModelByCriteria(employerCommunitiesCriteria);
                employerCommunities.put("communities", communities);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                employerCommunities.put("count", communities.size());
                result.put("employerCommunities", employerCommunities);
            }
            AccountTextCriteria memberCommunitiesCriteria = accountCriteriaMap.get("memberCommunitiesCriteria");
            if (memberCommunitiesCriteria != null) {
                Map<String, Object> memberCommunities = new HashMap<>();
                List<AccountModel> communities = profileModelService.getAccountsModelByCriteria(memberCommunitiesCriteria);
                memberCommunities.put("communities", communities);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                memberCommunities.put("count", communities.size());
                result.put("memberCommunities", memberCommunities);
            }
            AccountTextCriteria publisherCommunitiesCriteria = accountCriteriaMap.get("publisherCommunitiesCriteria");
            if (publisherCommunitiesCriteria != null) {
                Map<String, Object> publisherCommunities = new HashMap<>();
                List<AccountModel> communities = profileModelService.getAccountsModelByCriteria(publisherCommunitiesCriteria);
                publisherCommunities.put("communities", communities);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                publisherCommunities.put("count", communities.size());
                result.put("publisherCommunities", publisherCommunities);
            }
            AccountTextCriteria searchCommunitiesCriteria = accountCriteriaMap.get("searchCommunitiesCriteria");
            if (searchCommunitiesCriteria != null) {
                Map<String, Object> searchCommunities =new HashMap<>();
                List<AccountModel> communities = profileModelService.getAccountsModelByCriteria(searchCommunitiesCriteria);
                searchCommunities.put("communities", communities);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                searchCommunities.put("count", communities.size());
                result.put("searchCommunities", searchCommunities);
            }

            return Ajax.successResponse(result);

        } catch (Throwable e) {
            throw new RestException(e);
        }

    }

    @RequestMapping(value = "find_people", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getPeopleByCriteria(@RequestBody AccountTextCriteriaMap accountCriteriaMap) {
        try {

            Map<String, Object> result = new HashMap();

            AccountTextCriteria friendsCriteria = accountCriteriaMap.get("friendsCriteria");
            if (friendsCriteria != null) {
                Map<String, Object> friends = new HashMap<>();
                List<AccountModel> users = profileModelService.getAccountsModelByCriteria(friendsCriteria);
                friends.put("users", users);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                friends.put("count", users.size());
                result.put("userFriends", friends);
            }
            AccountTextCriteria publishersCriteria = accountCriteriaMap.get("publishersCriteria");
            if (publishersCriteria != null) {
                Map<String, Object> publishers = new HashMap<>();
                List<AccountModel> users = profileModelService.getAccountsModelByCriteria(publishersCriteria);
                publishers.put("users", users);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                publishers.put("count", users.size());
                result.put("userPublishers", publishers);
            }
            AccountTextCriteria searchPeopleCriteria = accountCriteriaMap.get("searchPeopleCriteria");
            if (searchPeopleCriteria != null) {
                Map<String, Object> searchPeople = new HashMap<>();
                List<AccountModel> users = profileModelService.getAccountsModelByCriteria(searchPeopleCriteria);
                searchPeople.put("users", users);
                // TODO : voronenko сервис кол-во AccountsModelByCriteria
                searchPeople.put("count", users.size());
                result.put("searchPeople", searchPeople);
            }

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
        return mv.addObject("account", accountModel);
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
