package com.heaptrip.web.modelservice;


import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.web.model.post.PostModel;

import java.util.List;

public interface PostModelService {

    /**
     * Convert post to post model
     *
     * @param post        post
     * @param isFullModel if the value is true, then all the data about the content
     *                    will be converted into the model (including the availability of the rating,
     *                    the possibility to add to favorites, etc.) Otherwise, the model will
     *                    include only short information to display in the feeds.
     * @return trip model
     */
    PostModel convertPost(Post post, boolean isFullModel);

    PostModel savePostModel(PostModel postModel);

    void updatePostModel(PostModel postModel);

    List<PostModel> getPostModelsByCriteria(FeedCriteria feedCriteria);

    PostModel getPostModelBytId(String contentId);
}
