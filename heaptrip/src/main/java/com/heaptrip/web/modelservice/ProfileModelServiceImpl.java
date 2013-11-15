package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.heaptrip.domain.entity.account.user.UserProfile;

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
            accountInfoModel.setProfile(convertProfileToProfileModel(account.getProfile()));
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

    private ProfileModel convertProfileToProfileModel(Profile profile) {
        ProfileModel profileModel = null;
        if (profile != null) {
            profileModel = new ProfileModel();
            profileModel.setId(profile.getId());
            profileModel.setDesc(profile.getDesc());
            profileModel.setCategories(convertCategoriesToModel(profile.getCategories()));
            profileModel.setRegions(convertRegionsToModel(profile.getRegions()));
            profileModel.setLangs(profile.getLangs());
            profileModel.setLocation(convertRegionToModel(profile.getLocation()));
        }
        return profileModel;

    }

    private UserProfile convertProfileToProfileModel(ProfileModel profileModel, UserProfileModel userProfileModel) {
        UserProfile profile = null;
        if (profileModel != null) {
            profile = new UserProfile();
            profile.setLangs(profileModel.getLangs());
            profile.setLocation(convertRegionModelToRegion(profileModel.getLocation(), getCurrentLocale()));
            profile.setCategories(convertCategoriesModelsToCategories(profileModel.getCategories(), getCurrentLocale()));
            profile.setDesc(profileModel.getDesc());
            profile.setRegions(convertRegionModelsToRegions(profileModel.getRegions(), getCurrentLocale()));
            profile.setId(profileModel.getId());
            if (userProfileModel.getBirthday() != null)
                profile.setBirthday(userProfileModel.getBirthday().getValue());

            // TODO: voronenko profile.setKnowledgies(); profile.setPractices();


        }
        return profile;

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
    public void updateProfileInfo(UserInfoModel accountInfoModel) {
        Assert.notNull(accountInfoModel, "accountInfoModel must not be null");
        Assert.notNull(accountInfoModel.getId(), "account id  must not be null");
        UserProfile profile = convertProfileToProfileModel(accountInfoModel.getProfile(), accountInfoModel.getUserProfile());
        userService.saveProfile(accountInfoModel.getId(), profile);
    }
}
