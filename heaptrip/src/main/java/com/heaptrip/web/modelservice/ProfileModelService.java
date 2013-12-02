package com.heaptrip.web.modelservice;

import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.UserInfoModel;

public interface ProfileModelService {

    UserInfoModel getProfileInformation(String uid);

    AccountModel getAccountInformation(String uid);

    void updateProfileInfo(UserInfoModel accountInfoModel);

}
