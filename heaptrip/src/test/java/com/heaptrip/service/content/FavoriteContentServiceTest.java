package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentFeedService;
import com.heaptrip.domain.service.content.FavoriteContentService;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class FavoriteContentServiceTest extends AbstractTestNGSpringContextTests {

    private String CONTENT_ID = "contentId4FavoriteContentServiceTest";

    private String OWNER_ID = "ownerId4FavoriteContentServiceTest";

    private String USER_ID = "userId4FavoriteContentServiceTest";

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private FavoriteContentService favoriteContentService;

    @Autowired
    private ContentFeedService contentFeedService;

    @BeforeClass
    public void beforeClass() {
        // create test content
        Content content = new Post();
        content.setId(CONTENT_ID);
        content.setOwnerId(OWNER_ID);
        content.setAllowed(new String[]{Content.ALLOWED_ALL_USERS});
        contentRepository.save(content);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // remove test content
        contentRepository.remove(CONTENT_ID);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        favoriteContentService.removeFavorites(CONTENT_ID, USER_ID);
    }

    @Test
    public void addFavorites() throws ExecutionException, InterruptedException {
        // check before call test
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        // call
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID).get();
        // check
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
    }

    @Test
    public void canAddFavorites() throws ExecutionException, InterruptedException {
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, OWNER_ID));
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID).get();
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, OWNER_ID));
    }

    @Test
    public void getFavoritesContents() throws ExecutionException, InterruptedException {
        // check before add favorite content
        MyAccountCriteria criteria = new MyAccountCriteria();
        criteria.setLocale(LanguageUtils.getEnglishLocale());
        criteria.setUserId(USER_ID);
        criteria.setContentType(ContentEnum.POST);
        criteria.setRelation(RelationEnum.FAVORITES);

        List<Content> list = contentFeedService.getContentsByMyAccountCriteria(criteria);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());

        // twice add favorite content
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID).get();
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID).get();

        // check after added
        list = contentFeedService.getContentsByMyAccountCriteria(criteria);
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void removeFavorites() throws ExecutionException, InterruptedException {
        // prepare
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID).get();
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        // call
        favoriteContentService.removeFavorites(CONTENT_ID, USER_ID);
        // check
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
    }
}
