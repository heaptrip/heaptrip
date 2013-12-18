package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.user.Knowledge;
import com.heaptrip.domain.entity.account.user.Practice;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserProfile;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.service.account.AccountService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    public UserInfoModel getProfileInformation(String uid) {
        Assert.notNull(uid, "user id  must not be null");
        User user = (User) accountService.getAccountById(uid);
        return convertUserToUserModel(user);
    }

    @Override
    public AccountModel getAccountInformation(String uid) {
        Assert.notNull(uid, "account id  must not be null");
        Account user = (Account) accountService.getAccountById(uid);
        return convertUserToUserModel(user);
    }

    private AccountModel convertUserToUserModel(Account account) {
        AccountModel accountModel = null;
        if (account != null) {
            accountModel = new AccountModel();
            putAccountToAccountModel(accountModel, account);
        }
        return accountModel;
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
            accountModel.setImage(new ImageModel(account.getImages().getProfileId()));
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
            profileModel.setKnowledgies(convertKnowledgiesToModels(userProfile.getKnowledgies()));
            profileModel.setPractices(convertPracticesToModels(userProfile.getPractices()));
        }
        return profileModel;
    }


    private KnowledgeModel[] convertKnowledgiesToModels(Knowledge[] knowledgies) {
        ArrayList<KnowledgeModel> knowledgeModelList = new ArrayList<>();
        if (knowledgies != null) {
            for (Knowledge knowledge : knowledgies) {
                knowledgeModelList.add(convertKnowledgeToModel(knowledge));
            }
        }
        return knowledgeModelList.toArray(new KnowledgeModel[knowledgeModelList.size()]);
    }

    private KnowledgeModel convertKnowledgeToModel(Knowledge knowledge) {
        KnowledgeModel knowledgeModel = null;
        if (knowledge != null) {
            knowledgeModel = new KnowledgeModel();
            knowledgeModel.setId(knowledge.getId());
            knowledgeModel.setBegin(convertDate(knowledge.getBegin()));
            knowledgeModel.setEnd(convertDate(knowledge.getEnd()));
            knowledgeModel.setDocument(knowledge.getDocument());
            knowledgeModel.setLocation(knowledge.getLocation());
            knowledgeModel.setSpecialist(knowledge.getSpecialist());
        }
        return knowledgeModel;
    }


    private Knowledge[] convertKnowledgiesModelsToKnowledgies(KnowledgeModel[] knowledgeModels) {
        ArrayList<Knowledge> knowledgeList = new ArrayList<>();
        if (knowledgeModels != null) {
            for (KnowledgeModel knowledgeModel : knowledgeModels) {
                knowledgeList.add(convertKnowledgeModelToKnowledge(knowledgeModel));
            }
        }
        return knowledgeList.toArray(new Knowledge[knowledgeList.size()]);
    }


    private Knowledge convertKnowledgeModelToKnowledge(KnowledgeModel knowledgeModel) {
        Knowledge knowledge = null;
        if (knowledgeModel != null) {
            knowledge = new Knowledge();
            knowledge.setId(knowledgeModel.getId());
            knowledge.setBegin(knowledgeModel.getBegin().getValue());
            knowledge.setEnd(knowledgeModel.getBegin().getValue());
            knowledge.setDocument(knowledgeModel.getDocument());
            knowledge.setLocation(knowledgeModel.getLocation());
            knowledge.setSpecialist(knowledgeModel.getSpecialist());
        }
        return knowledge;
    }


    private PracticeModel[] convertPracticesToModels(Practice[] knowledgies) {
        ArrayList<PracticeModel> practiceModelList = new ArrayList<>();
        if (knowledgies != null) {
            for (Practice practice : knowledgies) {
                practiceModelList.add(convertPracticeToModel(practice));
            }
        }
        return practiceModelList.toArray(new PracticeModel[practiceModelList.size()]);
    }

    private PracticeModel convertPracticeToModel(Practice practice) {
        PracticeModel practiceModel = null;
        if (practice != null) {
            practiceModel = new PracticeModel();
            practiceModel.setId(practice.getId());
            practiceModel.setBegin(convertDate(practice.getBegin()));
            practiceModel.setEnd(convertDate(practice.getEnd()));
            practiceModel.setDesc(practice.getDesc());
        }
        return practiceModel;
    }


    private Practice[] convertPracticesModelsToPractices(PracticeModel[] practiceModels) {
        ArrayList<Practice> practiceList = new ArrayList<>();
        if (practiceModels != null) {
            for (PracticeModel practiceModel : practiceModels) {
                practiceList.add(convertPracticeModelToPractice(practiceModel));
            }
        }
        return practiceList.toArray(new Practice[practiceList.size()]);
    }


    private Practice convertPracticeModelToPractice(PracticeModel practiceModel) {
        Practice practice = null;
        if (practiceModel != null) {
            practice = new Practice();
            practice.setId(practiceModel.getId());
            practice.setBegin(practiceModel.getBegin().getValue());
            practice.setEnd(practiceModel.getBegin().getValue());
            practice.setDesc(practiceModel.getDesc());
        }
        return practice;
    }


    private Profile convertProfileModelToProfile(AccountProfileModel accountProfileModel, UserProfileModel userProfileModel) {
        Profile profile = null;
        if (accountProfileModel != null) {
            if (userProfileModel != null) {
                UserProfile userProfile = new UserProfile();
                if (userProfileModel.getBirthday() != null)
                    userProfile.setBirthday(userProfileModel.getBirthday().getValue());
                userProfile.setKnowledgies(convertKnowledgiesModelsToKnowledgies(userProfileModel.getKnowledgies()));
                userProfile.setPractices(convertPracticesModelsToPractices(userProfileModel.getPractices()));
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
