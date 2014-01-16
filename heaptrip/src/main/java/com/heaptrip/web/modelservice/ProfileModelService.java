package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.CommunityInfoModel;
import com.heaptrip.web.model.profile.UserInfoModel;
import com.heaptrip.web.model.travel.TripInfoModel;

public interface ProfileModelService {

    UserInfoModel getProfileInformation(String uid);

    AccountModel getAccountInformation(String uid);

    CommunityInfoModel getCommunityInformation(String communityId);

    void updateUserInfo(UserInfoModel userInfoModel);

    Community saveCommunityInfo(CommunityInfoModel communityInfoModel);

    void updateCommunityInfo(CommunityInfoModel communityInfoModel);

}
