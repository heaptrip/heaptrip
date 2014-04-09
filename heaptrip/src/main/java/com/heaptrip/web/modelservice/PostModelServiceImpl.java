package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.post.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostModelServiceImpl extends ContentModelServiceImpl implements PostModelService {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostModel convertPost(Post post) {
        PostModel postModel = new PostModel();
        setContentToContentModel(ContentEnum.POST, postModel, post, getCurrentLocale(), false);
        return postModel;
    }

    @Override
    public List<PostModel> getPostModelsByCriteria(FeedCriteria feedCriteria) {
        feedCriteria.setLocale(getCurrentLocale());
        feedCriteria.setContentType(ContentEnum.POST);
        List<Content> contents = contentFeedService.getContentsByFeedCriteria(feedCriteria);
        List<PostModel> result = null;
        if (contents != null) {
            result = new ArrayList<>(contents.size());
            for (Content content : contents) {
                result.add(convertPost((Post) content));
            }
        }
        return result;
    }

    @Override
    public PostModel getPostModelBytId(String contentId) {
        Post post = postRepository.findOne(contentId);
        return (post == null) ? null : convertPost(post);
    }

    @Override
    public PostModel savePostModel(PostModel postModel) {
        Post post = convertContentModelToPost(postModel);
        post = postService.save(post);
        return (PostModel) convertContentToContentModel(ContentEnum.POST, post, false);
    }

    @Override
    public void updatePostModel(PostModel postModel) {
        Post post = convertContentModelToPost(postModel);
        postService.update(post);
    }

    protected Post convertContentModelToPost(ContentModel contentModel) {
        Post post = new Post();
        post.setId(contentModel.getId());
        post.setOwnerId(getCurrentUser().getId());
        post.setStatus(convertContentStatusModelToContentStatus(contentModel.getStatus()));
        post.setMainLang(contentModel.getLocale());
        post.setName(new MultiLangText(contentModel.getName()));
        post.setDescription(new MultiLangText(contentModel.getDescription()));
        post.setSummary(new MultiLangText(contentModel.getSummary()));
        post.setCategories(convertCategoriesModelsToCategories(contentModel.getCategories(), getCurrentLocale()));
        post.setRegions(convertRegionModelsToRegions(contentModel.getRegions(), getCurrentLocale()));
        return post;
    }
}
