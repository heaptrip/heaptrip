package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.account.criteria.AccountSearchReponse;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.CommunityInfoModel;
import com.heaptrip.web.model.profile.UserInfoModel;
import com.heaptrip.web.model.travel.TripInfoModel;

import java.util.List;

public interface ProfileModelService {

    AccountModel getAccountInformation(String uid);

    UserInfoModel getProfileInformation(String uid);

    CommunityInfoModel getCommunityInformation(String communityId);

    void updateUserInfo(UserInfoModel userInfoModel);

    Community saveCommunityInfo(CommunityInfoModel communityInfoModel);

    void updateCommunityInfo(CommunityInfoModel communityInfoModel);

    List<AccountModel> getAccountsModelByCriteria(AccountTextCriteria accountTextCriteria);

    AccountModel convertAccountToAccountModel(Account account);
}
