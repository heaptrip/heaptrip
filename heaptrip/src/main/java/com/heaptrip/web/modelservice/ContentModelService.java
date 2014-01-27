package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.profile.AccountModel;

import java.util.List;

public interface ContentModelService {

    public static String SERVICE_NAME = "contentModelService";

    AccountModel convertContentOwnerToModel(ContentOwner owner);

    ContentOwner getContentOwner();

    List<ContentModel> getContentModelsByCriteria(FeedCriteria feedCriteria);
}
