package com.heaptrip.web.modelservice;


import com.heaptrip.web.model.content.ContentModel;

public interface PostModelService {

    ContentModel savePostModel(ContentModel postModel);

    void updatePostModel(ContentModel postModel);

}
