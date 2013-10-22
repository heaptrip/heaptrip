package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.AccountInfoModel;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

    @Autowired
    private UserService userService;

    @Override
    public UserInfoModel getProfileInformation(String uid) {
        Assert.notNull(uid, "user id  must not be null");
        User user = userService.getUserById(uid);
        return convertUserToUserModel(user);
    }

    private UserInfoModel convertUserToUserModel(User account) {
        UserInfoModel userInfoModel = null;
        if (account != null) {
            userInfoModel = new UserInfoModel();
            putAccountToAccountInfoModel(userInfoModel, account);
        }
        return userInfoModel;
    }

    private AccountInfoModel putAccountToAccountInfoModel(AccountInfoModel accountInfoModel, Account account) {
        if (accountInfoModel != null && account != null) {
            putAccountToAccountModel(accountInfoModel, account);
            accountInfoModel.setEmail(account.getEmail());
        }
        return accountInfoModel;
    }


    private AccountModel putAccountToAccountModel(AccountModel accountModel, Account account) {
        if (accountModel != null && account != null) {
            accountModel.setId(account.getId());
            accountModel.setName(account.getName());
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
    public Trip saveProfileInfo(UserInfoModel accountInfoModel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateProfileInfo(UserInfoModel accountInfoModel) {

        //userService.saveProfile(null);

    }
}
