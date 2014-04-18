package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.web.model.content.ContentModel;

public interface ContentModelService {

    public static String SERVICE_NAME = "contentModelService";

    /**
     * Convert content to content model
     *
     * @param content     content
     * @param isFullModel if the value is true, then all the data about the content
     *                    will be converted into the model (including the availability of the rating,
     *                    the possibility to add to favorites, etc.) Otherwise, the model will
     *                    include only short information to display in the feeds.
     * @return content model
     */
    ContentModel convertContent(Content content, boolean isFullModel);
}
