package com.heaptrip.web.modelservice;


import com.heaptrip.web.model.post.PostModel;

public interface PostModelService {

    PostModel savePostModel(PostModel postModel);

    void updatePostModel(PostModel postModel);

}
