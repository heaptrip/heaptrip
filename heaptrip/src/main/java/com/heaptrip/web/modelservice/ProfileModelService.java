package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.CommunityInfoModel;
import com.heaptrip.web.model.profile.RegistrationInfoModel;
import com.heaptrip.web.model.profile.UserInfoModel;

import java.util.List;

public interface ProfileModelService {

    /**
     * attention !!! used to jsp (servlet) context webmvc-context.xml
     *
     * @param accountId - account id
     * @return account model
     */
    AccountModel getAccountInformation(String accountId);

    UserInfoModel getUserInformation(String accountId);

    CommunityInfoModel getCommunityInformation(String accountId);

    boolean isUserOwnsCommunity(String userId, String communityId);

    void updateUserInfo(UserInfoModel userInfoModel);

    User registration(RegistrationInfoModel registrationInfo);

    Community registration(CommunityInfoModel communityInfoModel);

    void updateCommunityInfo(CommunityInfoModel communityInfoModel);

    List<AccountModel> getAccountsModelByCriteria(AccountTextCriteria accountTextCriteria);

    void changeImage(String accountId, String imageId);

}
