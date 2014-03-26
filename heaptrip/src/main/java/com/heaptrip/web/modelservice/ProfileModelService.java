package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.model.profile.CommunityInfoModel;
import com.heaptrip.web.model.profile.UserInfoModel;
import java.util.List;

public interface ProfileModelService {

    /**
     * attention !!! used to jsp (servlet) context webmvc-context.xml
     * @param uid - account id
     * @return account model
     */
    AccountModel getAccountInformation(String uid);

    UserInfoModel getUserInformation(String uid);

    CommunityInfoModel getCommunityInformation(String communityId);

    void updateUserInfo(UserInfoModel userInfoModel);

    Community saveCommunityInfo(CommunityInfoModel communityInfoModel);

    void updateCommunityInfo(CommunityInfoModel communityInfoModel);

    List<AccountModel> getAccountsModelByCriteria(AccountTextCriteria accountTextCriteria);

}
