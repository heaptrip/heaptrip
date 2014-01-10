package com.heaptrip.service.content.post;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.util.language.LanguageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class PostServiceTest extends AbstractTestNGSpringContextTests {

    private static final String POST_ID = "POST_FOR_POST_SERVICE_TEST";

    private static final String OWNER_ID = "OWNER_FOR_POST_SERVICE_TEST";

    private static final Locale locale = LanguageUtils.getEnglishLocale();

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    private Post post = null;

    @BeforeClass
    public void beforeTest() {
        post = new Post();
        post.setId(POST_ID);
        ContentOwner owner = new ContentOwner();
        owner.setId(OWNER_ID);
        post.setName(new MultiLangText("Test post"));
        post.setSummary(new MultiLangText("Summary for test post"));
        post.setDescription(new MultiLangText("Description for test post"));
        post.setOwner(owner);
    }

    @AfterClass(alwaysRun = true)
    public void afterTest() {
        postService.hardRemove(POST_ID);
    }

    @Test(priority = 0, enabled = true)
    public void save() {
        // call
        postService.save(post);
        // check
        Post post = postRepository.findOne(POST_ID);
        Assert.assertNotNull(post);
        Assert.assertEquals(post, this.post);
        Assert.assertNotNull(post.getCreated());
        Assert.assertNull(post.getDeleted());
        Assert.assertTrue(ArrayUtils.isEmpty(post.getAllowed()));
        Assert.assertNull(post.getMainLang());
        Assert.assertTrue(ArrayUtils.isEmpty(post.getLangs()));
        Assert.assertNotNull(post.getOwner());
        Assert.assertNotNull(post.getOwner().getId());
        Assert.assertEquals(post.getOwner().getId(), OWNER_ID);
        Assert.assertNotNull(post.getStatus());
        Assert.assertNotNull(post.getStatus().getValue());
        Assert.assertEquals(post.getStatus().getValue(), ContentStatusEnum.DRAFT);
        Assert.assertNotNull(post.getRating());
        Assert.assertEquals(post.getRating().getCount(), 0);
        Assert.assertNotNull(post.getViews());
        Assert.assertEquals(post.getViews().getCount(), 0);
    }

    @Test(priority = 1, enabled = true)
    public void update() {
        // call
        Post post = postRepository.findOne(POST_ID);
        Assert.assertNotNull(post);
        String name = "New name for test post";
        String summary = "New summary for test post";
        String description = "New description for test post";
        post.getName().setValue(name);
        post.getSummary().setValue(summary);
        post.getDescription().setValue(description);
        postService.update(post);
        // check
        post = postRepository.findOne(POST_ID);
        Assert.assertNotNull(post);
        Assert.assertNotNull(post.getName());
        Assert.assertNotNull(post.getName().getValue(locale));
        Assert.assertEquals(post.getName().getValue(locale), name);
        Assert.assertNotNull(post.getSummary());
        Assert.assertNotNull(post.getSummary().getValue(locale));
        Assert.assertEquals(post.getSummary().getValue(locale), summary);
        Assert.assertNotNull(post.getDescription());
        Assert.assertNotNull(post.getDescription().getValue(locale));
        Assert.assertEquals(post.getDescription().getValue(locale), description);
    }

    @Test(priority = 2, enabled = true)
    public void getPosts() {
        // call
        String[] postIds = new String[]{POST_ID};
        List<Post> posts = postService.getPosts(postIds, LanguageUtils.getEnglishLocale());
        // check
        Assert.assertNotNull(posts);
        Assert.assertEquals(posts.size(), 1);
        Assert.assertEquals(posts.get(0), post);
    }
}
