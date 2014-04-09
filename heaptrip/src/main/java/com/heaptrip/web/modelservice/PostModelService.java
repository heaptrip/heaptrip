package com.heaptrip.web.modelservice;


import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.web.model.post.PostModel;

import java.util.List;

public interface PostModelService {

    PostModel convertPost(Post post);

    PostModel savePostModel(PostModel postModel);

    void updatePostModel(PostModel postModel);

    List<PostModel> getPostModelsByCriteria(FeedCriteria feedCriteria);

    PostModel getPostModelBytId(String contentId);
}
