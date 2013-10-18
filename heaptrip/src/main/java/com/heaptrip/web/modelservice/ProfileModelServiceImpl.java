package com.heaptrip.web.modelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.AccountInfoModel;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

	@Autowired
	private UserService userService;

	@Override
	public AccountInfoModel getProfileInformation(String uid) {
		Assert.notNull(uid, "user id  must not be null");
		User user = userService.getUserById(uid);
		return convertAccountToAccountModel(user);
	}

	private AccountInfoModel convertAccountToAccountModel(User account) {

		AccountInfoModel accountModel = null;

		if (account != null) {
			accountModel = new AccountInfoModel();
			accountModel.setName(account.getName());
			accountModel.setEmail(account.getEmail());
			accountModel.setRating(convertAccountRatingToRatingModel(account.getRating()));
			accountModel.setImage(new ImageModel(account.getImageProfileId()));
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

	@Override
	public Trip saveProfileInfo(AccountInfoModel accountInfoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateProfileInfo(AccountInfoModel accountInfoModel) {
		// TODO Auto-generated method stub
		
	}
}
