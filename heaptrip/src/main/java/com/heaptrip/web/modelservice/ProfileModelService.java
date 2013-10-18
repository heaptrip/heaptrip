package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.web.model.profile.AccountInfoModel;

public interface ProfileModelService {

	AccountInfoModel getProfileInformation(String uid);

	Trip saveProfileInfo(AccountInfoModel accountInfoModel);

	void updateProfileInfo(AccountInfoModel accountInfoModel);

}
