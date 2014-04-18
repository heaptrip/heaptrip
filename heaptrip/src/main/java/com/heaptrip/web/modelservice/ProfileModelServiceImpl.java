package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.community.CommunityProfile;
import com.heaptrip.domain.entity.account.community.agency.Agency;
import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.entity.account.community.company.Company;
import com.heaptrip.domain.entity.account.user.*;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.SystemException;
import com.heaptrip.domain.service.account.AccountService;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.profile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ProfileModelService {

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private AccountStoreService accountStoreService;


    @Override
    public UserInfoModel getUserInformation(String userId) {
        Assert.notNull(userId, "user id  must not be null");
        User user = (User) accountService.getAccountById(userId);
        return convertUserToUserModel(user);
    }

    @Override
    public CommunityInfoModel getCommunityInformation(String communityId) {
        Assert.notNull(communityId, "communityI id  must not be null");
        Community community = (Community) accountService.getAccountById(communityId);
        return convertCommunityToCommunityModel(community);
    }


    @Override
    public AccountModel getAccountInformation(String uid) {
        Assert.notNull(uid, "account id  must not be null");
        AccountModel result = null;
        if (uid != null) {
            Account account = accountStoreService.findOne(uid);
            if (account != null) {
                result = convertAccountToAccountModel(account);
            }
        }
        return result;
    }

    @Override
    public void updateUserInfo(UserInfoModel userInfoModel) {
        Assert.notNull(userInfoModel, "userInfoModel must not be null");
        Assert.notNull(userInfoModel.getId(), "user id  must not be null");
        Profile profile = convertProfileModelToProfile(userInfoModel.getAccountProfile(), userInfoModel.getUserProfile());
        userService.saveProfile(userInfoModel.getId(), profile);
    }

    @Override
    public boolean isUserOwnsCommunity(String userId, String communityId) {
        Community community = (Community) accountService.getAccountById(communityId);
        return community.getOwnerAccountId().equals(userId);
    }

    @Override
    public List<AccountModel> getAccountsModelByCriteria(AccountTextCriteria accountTextCriteria) {
        List<Account> accounts = accountStoreService.findByCriteria(accountTextCriteria);
        // TODO voronenko : dell comment code after test
        //accounts.add(accountService.getAccountById("53351da284aea2887e3a89f0"));
        convertAccountsToAccountModels(accounts);


        return convertAccountsToAccountModels(accounts);
    }

    @Override
    public void updateCommunityInfo(CommunityInfoModel communityInfoModel) {

        Assert.notNull(communityInfoModel, "communityInfoModel must not be null");
        Assert.notNull(communityInfoModel.getId(), "community id  must not be null");
        Profile profile = convertProfileModelToProfile(communityInfoModel.getAccountProfile(), communityInfoModel.getCommunityProfile());
        userService.saveProfile(communityInfoModel.getId(), profile);
    }


    @Override
    public void changeImage(String accountId, String imageId) {
        accountStoreService.changeImage(accountId, imageService.getImageById(imageId));
    }

    private AccountModel convertAccountToAccountModel(Account account) {
        AccountModel accountModel = null;
        if (account != null) {
            switch (account.getAccountType()) {
                case USER:
                    accountModel = convertUserToUserModel(account);
                    break;
                case COMPANY:
                case CLUB:
                case AGENCY:
                    accountModel = convertCommunityToCommunityModel(account);
                    break;
                default:
                    accountModel = new AccountModel();
                    putAccountToAccountModel(accountModel, account);
                    break;
            }
        }
        return accountModel;
    }


    private UserInfoModel convertUserToUserModel(Account user) {
        UserInfoModel userInfoModel = null;
        if (user != null) {
            userInfoModel = new UserInfoModel();
            putAccountToAccountInfoModel(userInfoModel, user);
            userInfoModel.setUserProfile(convertUserProfileToProfileModel(user.getProfile()));
        }
        return userInfoModel;
    }


    private CommunityInfoModel convertCommunityToCommunityModel(Account community) {
        CommunityInfoModel communityInfoModel = null;
        if (community != null) {
            communityInfoModel = new CommunityInfoModel();
            putAccountToAccountInfoModel(communityInfoModel, community);
            communityInfoModel.setCommunityProfile(convertCommunityProfileToProfileModel(community.getProfile()));
        }
        return communityInfoModel;
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
            if (account.getAccountType() != null)
                accountModel.setTypeAccount(account.getAccountType().name());
            if (account.getImage() != null && account.getImage().getRefs() != null) {
                accountModel.setImage(convertImage(account.getImage()));
            }
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
        if (profile != null) {
            UserProfile userProfile = (UserProfile) profile;
            profileModel = new UserProfileModel();
            profileModel.setBirthday(convertDate(userProfile.getBirthday()));
            profileModel.setKnowledgies(convertKnowledgiesToModels(userProfile.getKnowledgies()));
            profileModel.setPractices(convertPracticesToModels(userProfile.getPractices()));
        }
        return profileModel;
    }

    private CommunityProfileModel convertCommunityProfileToProfileModel(Profile profile) {
        CommunityProfileModel profileModel = null;
        if (profile != null) {
            CommunityProfile communityProfile = (CommunityProfile) profile;
            profileModel = new CommunityProfileModel();
            profileModel.setSkype(communityProfile.getSkype());
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
            putProfileModelInfoToProfile(profile, accountProfileModel);
        }
        return profile;

    }

    private Profile convertProfileModelToProfile(AccountProfileModel accountProfileModel, CommunityProfileModel communityProfileModel) {
        Profile profile = null;
        if (accountProfileModel != null) {
            if (communityProfileModel != null) {
                CommunityProfile communityProfile = new CommunityProfile();
                communityProfile.setSkype(communityProfileModel.getSkype());
            } else {
                profile = new Profile();
            }
            putProfileModelInfoToProfile(profile, accountProfileModel);
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

    private void putProfileModelInfoToProfile(Profile profile, AccountProfileModel accountProfileModel) {
        if (accountProfileModel != null) {
            profile.setLangs(accountProfileModel.getLangs());
            profile.setLocation(convertRegionModelToRegion(accountProfileModel.getLocation(), getCurrentLocale()));
            profile.setCategories(convertCategoriesModelsToCategories(accountProfileModel.getCategories(), getCurrentLocale()));
            profile.setDesc(accountProfileModel.getDesc());
            profile.setRegions(convertRegionModelsToRegions(accountProfileModel.getRegions(), getCurrentLocale()));
            profile.setId(accountProfileModel.getId());
        }
    }

    private List<AccountModel> convertAccountsToAccountModels(List<Account> accounts) {
        ArrayList<AccountModel> accountModels = new ArrayList<>();
        if (accounts != null) {
            for (Account account : accounts) {
                accountModels.add(convertAccountToAccountModel(account));

            }
        }
        return accountModels;
    }


    @Override
    public Community registration(CommunityInfoModel communityInfoModel) {
        Community community;
        switch (AccountEnum.valueOf(communityInfoModel.getTypeAccount())) {
            case COMPANY:
                community = new Company();
                break;
            case CLUB:
                community = new Club();
                break;
            case AGENCY:
                community = new Agency();
                break;
            default:
                throw new SystemException("Error accountType: " + communityInfoModel.getTypeAccount());

        }
        community.setName(communityInfoModel.getName());
        community.setEmail(communityInfoModel.getEmail());
        community.setOwnerAccountId(getCurrentUser().getId());
        community.setImage(convertImage(communityInfoModel.getImage()));
        community.setProfile(convertProfileModelToProfile(communityInfoModel.getAccountProfile(), communityInfoModel.getCommunityProfile()));
        community = communityService.registration(community, getCurrentLocale());
        return community;
    }

    @Override
    public User registration(RegistrationInfoModel regInfo) {

        UserRegistration userReg = new UserRegistration();

        String[] roles = {"ROLE_USER"};
        InputStream photo = null;

        userReg.setEmail(regInfo.getEmail());
        userReg.setName(regInfo.getFirstName() + " " + regInfo.getSecondName());
        userReg.setPassword(regInfo.getPassword());
        userReg.setRoles(roles);

        if (regInfo.getSocNetName() != null && !regInfo.getSocNetName().isEmpty() && regInfo.getSocNetUserUID() != null
                && !regInfo.getSocNetUserUID().isEmpty()) {
            photo = new HttpClient().doInputStreamGet(regInfo.getPhotoUrl());
            userReg.setNet(new SocialNetwork[]{new SocialNetwork(procsessSocNetName(regInfo.getSocNetName()), regInfo
                    .getSocNetUserUID())});
        }

        User user = null;

        try {
            try {
                user = userService.registration(userReg, photo, getCurrentLocale());
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }

    private SocialNetworkEnum procsessSocNetName(String socNetName) {
        SocialNetworkEnum result = null;
        if (socNetName.equals(FaceBookAPIService.SOC_NET_NAME)) {
            result = SocialNetworkEnum.FB;
        } else if (socNetName.equals(VKontakteAPIService.SOC_NET_NAME)) {
            result = SocialNetworkEnum.VK;
        }
        return result;
    }


}
