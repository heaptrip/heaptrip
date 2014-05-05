package com.heaptrip.web.jsp;

import com.heaptrip.web.model.profile.AccountModel;
import com.heaptrip.web.modelservice.ProfileModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.04.14
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ProfileServiceWrapperImpl implements ProfileServiceWrapper {

    @Autowired
    ProfileModelService profileModelService;


    @Override
    public AccountModel getAccountInformation(String accountId) {
        return profileModelService.getAccountInformation(accountId);
    }

    @Override
    public boolean isUserOwnsCommunity(String userId, String communityId) {
        return profileModelService.isUserOwnsCommunity(userId, communityId);
    }
}
