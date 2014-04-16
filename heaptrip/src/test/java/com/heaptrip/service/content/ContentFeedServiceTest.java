package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.ContentFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentFeedServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ContentFeedService contentFeedService;

    @Autowired
    private ContentRepository contentRepository;

    private Post post = null;

    @BeforeClass
    public void init() {
        post = new Post();
        post.setId(FeedDataProvider.CONTENT_ID);
        post.setOwnerId(FeedDataProvider.OWNER_ID);
        post.setAllowed(new String[]{FeedDataProvider.USER_ID});
        post.setCategoryIds(FeedDataProvider.CATEGORY_IDS);
        post.setRegionIds(FeedDataProvider.REGION_IDS);
        ContentStatus status = new ContentStatus();
        status.setValue(ContentStatusEnum.PUBLISHED_FRIENDS);
        post.setStatus(status);
        contentRepository.save(post);
    }

    @AfterClass(alwaysRun = true)
    public void release() {
        contentRepository.remove(FeedDataProvider.CONTENT_ID);
    }

    @Test(priority = 0, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "feedCriteria")
    public void getContentsByFeedCriteria(FeedCriteria feedCriteria) {
        // call
        List<Content> contents = contentFeedService.getContentsByFeedCriteria(feedCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), post);
    }

    @Test(priority = 1, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getContentsByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
        // call
        List<Content> contents = contentFeedService.getContentsByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), post);
    }

    @Test(priority = 2, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getContentsByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
        // call
        List<Content> contents = contentFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertNotNull(contents);
        Assert.assertEquals(contents.size(), 1);
        Assert.assertEquals(contents.get(0), post);
    }

    @Test(priority = 3, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "feedCriteria")
    public void getCountByFeedCriteria(FeedCriteria feedCriteria) {
        // call
        long count = contentFeedService.getCountByFeedCriteria(feedCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(priority = 4, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "myAccountCriteria")
    public void getCountByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
        // call
        long count = contentFeedService.getCountByMyAccountCriteria(myAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }

    @Test(priority = 5, enabled = true, dataProviderClass = FeedDataProvider.class, dataProvider = "foreignAccountCriteria")
    public void getCountByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
        // call
        long count = contentFeedService.getCountByForeignAccountCriteria(foreignAccountCriteria);
        // check
        Assert.assertEquals(count, 1);
    }
}
