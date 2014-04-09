package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.FavoriteContentService;
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

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class FavoriteContentServiceTest extends AbstractTestNGSpringContextTests {

    private String CONTENT_ID = "contentId4FavoriteContentServiceTest";

    private String OWNER_ID = "ownerId4FavoriteContentServiceTest";

    private String USER_ID = "userId4FavoriteContentServiceTest";

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private FavoriteContentService favoriteContentService;

    @BeforeClass
    public void beforeClass() {
        // create test content
        Content content = new Post();
        content.setId(CONTENT_ID);
        content.setOwnerId(OWNER_ID);
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
    public void addFavorites() {
        // check before call test
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        // call
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID);
        // check
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
    }

    @Test
    public void canAddFavorites() {
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, OWNER_ID));
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID);
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, OWNER_ID));
    }

    @Test
    public void getFavoritesContents() {
        // check before add favorite content
        List<Content> list = favoriteContentService.getFavoritesContents(ContentEnum.POST, USER_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
        list = favoriteContentService.getFavoritesContents(USER_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
        // twice add favorite content
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID);
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID);
        // check after added
        list = favoriteContentService.getFavoritesContents(ContentEnum.POST, USER_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);
        list = favoriteContentService.getFavoritesContents(USER_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void removeFavorites() {
        // prepare
        favoriteContentService.addFavorites(CONTENT_ID, USER_ID);
        Assert.assertFalse(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
        // call
        favoriteContentService.removeFavorites(CONTENT_ID, USER_ID);
        // check
        Assert.assertTrue(favoriteContentService.canAddFavorites(CONTENT_ID, USER_ID));
    }
}
