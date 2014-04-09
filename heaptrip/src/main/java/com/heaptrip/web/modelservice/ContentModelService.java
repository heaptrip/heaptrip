package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.web.model.content.ContentModel;

public interface ContentModelService {

    public static String SERVICE_NAME = "contentModelService";

    ContentModel convertContent(Content content);
}
