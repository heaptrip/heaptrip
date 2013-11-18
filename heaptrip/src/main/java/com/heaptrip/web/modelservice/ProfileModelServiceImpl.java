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
            userInfoModel.setUserProfile(convertUserProfileToProfileModel(account.getProfile()));
        }
        return userInfoModel;
    }

    private AccountInfoModel putAccountToAccountInfoModel(AccountInfoModel accountInfoModel, Account account) {
        if (accountInfoModel != null && account != null) {
            putAccountToAccountModel(accountInfoModel, account);
            accountInfoModel.setEmail(account.getEmail());
            accountInfoModel.setAccountProfile(convertAccountProfileToProfileModel(account.getProfile()));
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

    private AccountProfileModel convertAccountProfileToProfileModel(Profile profile) {
        AccountProfileModel accountProfileModel = null;
        if (profile != null) {
            accountProfileModel = new AccountProfileModel();
            accountProfileModel.setId(profile.getId());
            accountProfileModel.setDesc(profile.getDesc());
            accountProfileModel.setCategories(convertCategoriesToModel(profile.getCategories()));
            accountProfileModel.setRegions(convertRegionsToModel(profile.getRegions()));
            accountProfileModel.setLangs(profile.getLangs());
            accountProfileModel.setLocation(convertRegionToModel(profile.getLocation()));
        }
        return accountProfileModel;
    }


    private UserProfileModel convertUserProfileToProfileModel(Profile profile) {
        UserProfileModel profileModel = null;
        if (profile != null && (profile instanceof UserProfile)) {
            UserProfile userProfile = (UserProfile) profile;
            profileModel = new UserProfileModel();
            profileModel.setBirthday(convertDate(userProfile.getBirthday()));
        }
        return profileModel;
    }

    private Profile convertProfileModelToProfile(AccountProfileModel accountProfileModel, UserProfileModel userProfileModel) {
        Profile profile = null;
        if (accountProfileModel != null) {
            if (userProfileModel != null) {
                UserProfile userProfile = new UserProfile();
                if (userProfileModel.getBirthday() != null)
                    userProfile.setBirthday(userProfileModel.getBirthday().getValue());
                // TODO: voronenko profile.setKnowledgies(); profile.setPractices();
                profile = userProfile;
            } else {
                profile = new Profile();
            }
            profile.setLangs(accountProfileModel.getLangs());
            profile.setLocation(convertRegionModelToRegion(accountProfileModel.getLocation(), getCurrentLocale()));
            profile.setCategories(convertCategoriesModelsToCategories(accountProfileModel.getCategories(), getCurrentLocale()));
            profile.setDesc(accountProfileModel.getDesc());
            profile.setRegions(convertRegionModelsToRegions(accountProfileModel.getRegions(), getCurrentLocale()));
            profile.setId(accountProfileModel.getId());
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
        Profile profile = convertProfileModelToProfile(accountInfoModel.getAccountProfile(), accountInfoModel.getUserProfile());
        userService.saveProfile(accountInfoModel.getId(), profile);
    }
}
