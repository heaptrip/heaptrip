package com.heaptrip.web.modelservice;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.web.model.profile.AccountModel;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

	@Override
	public AccountModel getProfileInformation(String uid) {

		Assert.notNull(uid, "user id  must not be null");

		AccountModel accountModel = new AccountModel();

		return accountModel;
	}

}
