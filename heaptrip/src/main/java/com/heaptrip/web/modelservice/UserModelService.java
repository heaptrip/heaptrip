package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.web.model.profile.RegistrationInfoModel;


public interface UserModelService {

	User registration(RegistrationInfoModel registrationInfo);

}
