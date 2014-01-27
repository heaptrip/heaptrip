package com.heaptrip.service.content.post;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Views;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.service.content.ContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class PostServiceImpl extends ContentServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ContentSearchService contentSearchService;

    @Override
    public Post save(Post content) {
        Assert.notNull(content, "content must not be null");
        Assert.notNull(content.getOwner(), "owner must not be null");
        Assert.notNull(content.getOwner().getId(), "owner.id must not be null");
        Assert.notEmpty(content.getName(), "name must not be empty");
        Assert.notEmpty(content.getSummary(), "summary must not be empty");
        Assert.notEmpty(content.getDescription(), "description must not be empty");

        // TODO konovalov: read and set owner name, account type and rating
        content.getOwner().setName("Ivan Petrov");
        content.getOwner().setRating(0D);
        content.getOwner().setType(AccountEnum.USER);
        content.setAllowed(new String[]{"0"});

        // TODO konovalov: if owner account type == (CLUB or COMPANY) then set owners
        content.setOwners(new String[]{content.getOwner().getId()});

        // update categories and categoryIds
        updateCategories(content);

        // update regions and regionIds
        updateRegions(content);

        content.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
        content.setCreated(new Date());
        content.setDeleted(null);
        content.setRating(ContentRating.getDefaultValue());
        content.setComments(0L);

        Views views = new Views();
        views.setCount(0);
        content.setViews(views);

        // save to mongodb
        postRepository.save(content);

        // save to solr
        contentSearchService.saveContent(content.getId());

        return content;
    }

    @Override
    public void remove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        super.remove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    @Override
    public void hardRemove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        super.hardRemove(contentId);
        // remove from solr
        contentSearchService.removeContent(contentId);
    }

    @Override
    public void update(Post post) {
        Assert.notNull(post, "post must not be null");
        Assert.notNull(post.getId(), "post.id must not be null");
        Assert.notEmpty(post.getName(), "name must not be empty");
        Assert.notEmpty(post.getSummary(), "summary must not be empty");
        Assert.notEmpty(post.getDescription(), "description must not be empty");

        // update categories and categoryIds
        updateCategories(post);

        // update regions and regionIds
        updateRegions(post);

        // update to db
        postRepository.update(post);

        // save to solr
        contentSearchService.saveContent(post.getId());
    }

    @Override
    public List<Post> getPosts(String[] postIds, Locale locale) {
        Assert.notNull(postIds, "postIds must not be null");
        Assert.notNull(locale, "locale must not be null");
        return postRepository.findByIds(postIds, locale);
    }

}
