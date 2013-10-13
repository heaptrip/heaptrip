package com.heaptrip.web.modelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.AccountModel;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

	@Autowired
	private UserService userService;

	@Override
	public AccountModel getProfileInformation(String uid) {
		Assert.notNull(uid, "user id  must not be null");
		User user = userService.getUserById(uid);
		return convertAccountToAccountModel(user);
	}

	private AccountModel convertAccountToAccountModel(User account) {

		AccountModel accountModel = null;

		if (account != null) {
			accountModel = new AccountModel();
			accountModel.setName(account.getName());
			accountModel.setEmail(account.getEmail());
			accountModel.setRating(convertAccountRatingToRatingModel(account.getRating()));
			accountModel.setImage(account.getImageProfileId());
		}

		return accountModel;
	}

	private RatingModel convertAccountRatingToRatingModel(AccountRating accountRating) {
		RatingModel ratingModel = new RatingModel();
		if (accountRating != null) {
			ratingModel.setValue(accountRating.getValue());
			ratingModel.setCount(accountRating.getCount());
		} else {
			ratingModel.setValue(0D);
			ratingModel.setCount(0);
		}
		return ratingModel;
	}
}
