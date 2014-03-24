package com.heaptrip.repository.account.community;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.repository.account.community.CommunityRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityRepositoryImpl extends CrudRepositoryImpl<Community> implements CommunityRepository {

    @Override
    protected String getCollectionName() {
        return CollectionEnum.ACCOUNTS.getName();
    }

    @Override
    protected Class<Community> getCollectionClass() {
        return Community.class;
    }

}
