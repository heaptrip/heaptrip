package com.heaptrip.repository.account.community;

import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Repository
public class CommunityRepositoryImpl extends CrudRepositoryImpl<Community> implements CommunityRepository {

	@Override
	protected String getCollectionName() {
		return Account.COLLECTION_NAME;
	}

	@Override
	protected Class<Community> getCollectionClass() {
		return Community.class;
	}

}
