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
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.util.converter.Converter;
import com.heaptrip.util.converter.ListConverter;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.util.tuple.TreObject;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.profile.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InputStream;
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

    @Autowired
    private FilterModelService filterModelService;

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
        accountService.saveProfile(userInfoModel.getId(), userInfoModel.getName(), profile);
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
    public List<AccountModel> getAccountsModelByRelationCriteria(RelationCriteria relationCriteria) {
        List<Account> accounts = new ArrayList<>();//  accountStoreService.findByCriteria(accountTextCriteria);
        accounts.add(accountStoreService.findOne("53959373a09ee8af6c75aebd"));
        convertAccountsToAccountModels(accounts);

        return convertAccountsToAccountModels(accounts);
    }

    @Override
    public void updateCommunityInfo(CommunityInfoModel communityInfoModel) {
        Assert.notNull(communityInfoModel, "communityInfoModel must not be null");
        Assert.notNull(communityInfoModel.getId(), "community id  must not be null");
        Profile profile = convertProfileModelToProfile(communityInfoModel.getAccountProfile(), communityInfoModel.getCommunityProfile());
        accountService.saveProfile(communityInfoModel.getId(), communityInfoModel.getName(), profile);
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
            accountProfileModel.setDesc(profile.getDesc());
            accountProfileModel.setCategories(convertCategoriesToModel(profile.getCategories()));
            accountProfileModel.setRegions(convertRegionsToModel(profile.getRegions()));
            accountProfileModel.setLangs(profile.getLangs());
            accountProfileModel.setLocation(convertRegionToModel(profile.getLocation()));
            if (accountProfileModel.getLocation() != null && accountProfileModel.getLocation().getId() != null) {
                TreObject<RegionModel, RegionModel, RegionModel> regions = filterModelService.getRegionHierarchy(accountProfileModel.getLocation().getId());
                StringBuilder region = new StringBuilder();
                region.append(regions.getUno() == null ? "" : regions.getUno().getData());
                region.append(regions.getDue() == null ? "" : ", " + regions.getDue().getData());
                region.append(regions.getTre() == null ? "" : ", " + regions.getTre().getData());
                accountProfileModel.getLocation().setData(region.toString());
            }
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
        if (knowledgeModels == null || knowledgeModels.length == 0) {
            return null;
        } else {
            return ListConverter.convertList(knowledgeModels, new Converter<KnowledgeModel, Knowledge>() {
                public Knowledge convert(KnowledgeModel knowledgeModel) {
                    return convertKnowledgeModelToKnowledge(knowledgeModel);
                }
            });
        }
    }

    private Knowledge convertKnowledgeModelToKnowledge(KnowledgeModel knowledgeModel) {
        Knowledge knowledge = null;
        if (knowledgeModel != null) {
            knowledge = new Knowledge();
            knowledge.setId(knowledgeModel.getId());
            knowledge.setBegin(knowledgeModel.getBegin().getValue());
            knowledge.setEnd(knowledgeModel.getEnd().getValue());
            knowledge.setDocument(knowledgeModel.getDocument());
            knowledge.setLocation(knowledgeModel.getLocation());
            knowledge.setSpecialist(knowledgeModel.getSpecialist());
        }
        return knowledge;
    }

    private PracticeModel[] convertPracticesToModels(Practice[] practice) {
        ArrayList<PracticeModel> practicModelList = new ArrayList<>();
        if (practice != null) {
            for (Practice practic : practice) {
                practicModelList.add(convertPracticeToModel(practic));
            }
        }
        return practicModelList.toArray(new PracticeModel[practicModelList.size()]);
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
        if (practiceModels == null || practiceModels.length == 0) {
            return null;
        } else {
            return ListConverter.convertList(practiceModels, new Converter<PracticeModel, Practice>() {
                public Practice convert(PracticeModel practiceModel) {
                    return convertPracticeModelToPractice(practiceModel);
                }
            });
        }
    }

    private Practice convertPracticeModelToPractice(PracticeModel practiceModel) {
        Practice practice = null;
        if (practiceModel != null) {
            practice = new Practice();
            practice.setId(practiceModel.getId());
            practice.setBegin(practiceModel.getBegin().getValue());
            practice.setEnd(practiceModel.getEnd().getValue());
            practice.setDesc(practiceModel.getDesc());
        }
        return practice;
    }

    private UserProfile convertProfileModelToProfile(AccountProfileModel accountProfileModel, UserProfileModel userProfileModel) {
        UserProfile profile = null;
        if (accountProfileModel != null) {
            if (userProfileModel != null) {
                UserProfile userProfile = new UserProfile();
                if (accountProfileModel.getLocation() != null) {
                    userProfile.setLocation(convertRegionModelToRegion(accountProfileModel.getLocation(), getCurrentLocale()));
                }
                if (userProfileModel.getBirthday() != null)
                    userProfile.setBirthday(userProfileModel.getBirthday().getValue());
                userProfile.setKnowledgies(convertKnowledgiesModelsToKnowledgies(userProfileModel.getKnowledgies()));
                userProfile.setPractices(convertPracticesModelsToPractices(userProfileModel.getPractices()));
                profile = userProfile;
            } else {
                profile = new UserProfile();
            }
            putProfileModelInfoToProfile(profile, accountProfileModel);
        }
        return profile;

    }

    private CommunityProfile convertProfileModelToProfile(AccountProfileModel accountProfileModel, CommunityProfileModel communityProfileModel) {
        CommunityProfile profile = null;
        if (accountProfileModel != null) {
            if (communityProfileModel != null) {
                CommunityProfile communityProfile = new CommunityProfile();
                if (accountProfileModel.getLocation() != null) {
                    communityProfile.setLocation(convertRegionModelToRegion(accountProfileModel.getLocation(), getCurrentLocale()));
                }
                communityProfile.setSkype(communityProfileModel.getSkype());
            } else {
                profile = new CommunityProfile();
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
        }
    }

    private List<AccountModel> convertAccountsToAccountModels(List<Account> accounts) {
        return ListConverter.convertList(accounts, new Converter<Account, AccountModel>() {
            public AccountModel convert(Account account) {
                return convertAccountToAccountModel(account);
            }
        });
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
        community.setProfile(convertProfileModelToProfile(communityInfoModel.getAccountProfile(), communityInfoModel.getCommunityProfile()));
        community = communityService.registration(community, getCurrentLocale());
        return community;
    }

    @Override
    public User registration(RegistrationInfoModel regInfo) {

        User user = new User();

        String[] roles = {"ROLE_USER"};
        InputStream photo = null;

        user.setEmail(regInfo.getEmail());
        user.setName(regInfo.getFirstName() + " " + regInfo.getSecondName());
        user.setRoles(roles);

        if (regInfo.getSocNetName() != null && !regInfo.getSocNetName().isEmpty() && regInfo.getSocNetUserUID() != null
                && !regInfo.getSocNetUserUID().isEmpty()) {
            photo = new HttpClient().doInputStreamGet(regInfo.getPhotoUrl());
            user.setNet(new SocialNetwork[]{new SocialNetwork(procsessSocNetName(regInfo.getSocNetName()), regInfo
                    .getSocNetUserUID())});
        }

        User savedUser = null;

        try {
            savedUser = userService.registration(user, regInfo.getPassword(), photo, getCurrentLocale());
        } catch (Exception e) {
            // TODO voronenko: process exception
            e.printStackTrace();
        }

        return savedUser;
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
