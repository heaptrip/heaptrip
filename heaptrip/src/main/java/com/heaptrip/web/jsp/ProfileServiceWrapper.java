package com.heaptrip.web.jsp;

import com.heaptrip.web.model.profile.AccountModel;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.04.14
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public interface ProfileServiceWrapper {

    AccountModel getAccountInformation(String accountId);

    boolean isUserOwnsCommunity(String userId, String communityId);

}
