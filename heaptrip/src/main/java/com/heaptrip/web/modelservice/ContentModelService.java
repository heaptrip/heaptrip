package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.profile.AccountModel;

import java.util.List;

public interface ContentModelService {

    public static String SERVICE_NAME = "contentModelService";

    List<ContentModel> getContentModelsByCriteria(FeedCriteria feedCriteria);

    ContentModel getContentModelByContentId(String contentId, ContentEnum contentType);
}
