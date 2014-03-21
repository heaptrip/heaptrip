package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.FavoriteContentService;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.feed.ContentFeedService;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class FavoriteContentServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private FavoriteContentService favoriteContentService;

    @Autowired
    private ContentFeedService contentFeedService;

    @Test(priority = 0, enabled = true, dataProviderClass = ContentDataProvider.class, dataProvider = "favoritesMyAccountCriteria")
    public void addFavorites(MyAccountCriteria myAccountCriteria) {
        // call
        favoriteContentService.addFavorites(ContentDataProvider.CONTENT_ID, ContentDataProvider.USER_ID);
        // check that twice can not be added
        favoriteContentService.addFavorites(ContentDataProvider.CONTENT_ID, ContentDataProvider.USER_ID);
        // check
        long count = contentFeedService.getCountByMyAccountCriteria(myAccountCriteria);
        Assert.assertEquals(count, 1);
    }

    @Test(priority = 1, enabled = true)
    public void isFavorites() {
        // call
        boolean isFavorite = favoriteContentService.isFavorites(ContentDataProvider.CONTENT_ID,
                ContentDataProvider.USER_ID);
        // check
        Assert.assertTrue(isFavorite);
    }

    @Test(priority = 2, enabled = true)
    public void getFavoritesContents() {
        // call
        List<Content> list = favoriteContentService.getFavoritesContents(ContentEnum.POST, ContentDataProvider.USER_ID,
                LanguageUtils.getEnglishLocale());
        // check
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        // call
        list = favoriteContentService.getFavoritesContents(ContentDataProvider.USER_ID,
                LanguageUtils.getEnglishLocale());
        // check
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test(priority = 3, enabled = true, dataProvider = "favoritesMyAccountCriteria", dataProviderClass = ContentDataProvider.class)
    public void removeFavorites(MyAccountCriteria myAccountCriteria) {
        // call
        favoriteContentService.removeFavorites(ContentDataProvider.CONTENT_ID, ContentDataProvider.USER_ID);
        // check
        long count = contentFeedService.getCountByMyAccountCriteria(myAccountCriteria);
        Assert.assertEquals(count, 0);
    }
}
