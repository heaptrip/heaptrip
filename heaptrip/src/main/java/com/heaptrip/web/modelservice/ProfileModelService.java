package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.web.model.profile.UserInfoModel;

public interface ProfileModelService {

    UserInfoModel getProfileInformation(String uid);

    void updateProfileInfo(UserInfoModel accountInfoModel);

}
