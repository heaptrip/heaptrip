package com.heaptrip.web.converter;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.web.model.user.RegistrationInfoModel;


public interface UserModelService {

	User registration(RegistrationInfoModel registrationInfo);

}
