package com.heaptrip.domain.repository.account.user;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User> {

	User findUserBySocNetUID(String socNetName, String uid);

	Boolean isEmptyPassword(String userId);

	void changePassword(String userId, String newPassword);

	void profileImageFrom(String userId, SocialNetworkEnum socialNetwork);

	void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork);

	void linkSocialNetwork(String userId, SocialNetwork socialNetwork);

    List<User> findUsersByEmail(String email);

    List<User> findUsersByEmailAndStatus(String email, AccountStatusEnum accountStatus);
}
