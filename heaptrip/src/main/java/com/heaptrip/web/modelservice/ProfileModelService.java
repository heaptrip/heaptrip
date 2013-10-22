package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.web.model.profile.UserInfoModel;

public interface ProfileModelService {

	UserInfoModel getProfileInformation(String uid);

	Trip saveProfileInfo(UserInfoModel accountInfoModel);

	void updateProfileInfo(UserInfoModel accountInfoModel);

}
