package com.heaptrip.repository.account.community;

import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Repository
public class CommunityRepositoryImpl extends CrudRepositoryImpl<Account> implements CommunityRepository {

	@Override
	protected String getCollectionName() {
		return Account.COLLECTION_NAME;
	}

	@Override
	protected Class<Account> getCollectionClass() {
		return Account.class;
	}

}
