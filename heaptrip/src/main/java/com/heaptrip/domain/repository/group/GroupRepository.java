package com.heaptrip.domain.repository.group;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Account> {

	void confirmRegistration(String groupId);
	
	void changeEmail(String groupId, String newEmail);
	
	void saveSetting(String groupId, Setting setting);
	
	void saveProfile(String groupId, Profile profile);
	
	void deleteGroup(String groupId);
}
