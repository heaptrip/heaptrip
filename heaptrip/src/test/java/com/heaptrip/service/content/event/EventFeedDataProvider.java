package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import org.testng.annotations.DataProvider;

import java.util.Locale;

public class EventFeedDataProvider {

    static String[] CATEGORY_IDS = new String[]{"2.2", "3.1"};

    static String[] REGION_IDS = new String[]{"10", "70", "80"};

    static String[] EVENT_TYPE_IDS = new String[]{"1", "2", "3"};

    static String CONTENT_ID = "CONTENT_FOR_EVENT_FEED_SERVICE_TEST";

    static String OWNER_ID = "OWNER_FOR_EVENT_FEED_SERVICE_TEST";

    static String USER_ID = "USER_FOR_EVENT_FEED_SERVICE_TEST";

    @DataProvider(name = "feedCriteria")
    public static Object[][] getTripFeedCriteria() {
        EventFeedCriteria criteria = new EventFeedCriteria();
        criteria.setContentType(ContentEnum.EVENT);
        criteria.setUserId(USER_ID);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
        criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
        criteria.setTypes(new IDListCriteria(CheckModeEnum.IN, EVENT_TYPE_IDS));
        criteria.setSkip(0L);
        criteria.setLimit(10L);
        criteria.setSort(ContentSortEnum.CREATED);
        criteria.setLocale(Locale.ENGLISH);
        return new Object[][]{new Object[]{criteria}};
    }

    @DataProvider(name = "myAccountCriteria")
    public static Object[][] getTripMyAccountCriteria() {
        EventMyAccountCriteria criteria = new EventMyAccountCriteria();
        criteria.setContentType(ContentEnum.EVENT);
        criteria.setUserId(OWNER_ID);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
        criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
        criteria.setTypes(new IDListCriteria(CheckModeEnum.IN, EVENT_TYPE_IDS));
        criteria.setSkip(0L);
        criteria.setLimit(10L);
        criteria.setSort(ContentSortEnum.RATING);
        criteria.setStatus(new ContentStatusEnum[]{ContentStatusEnum.PUBLISHED_FRIENDS});
        criteria.setLocale(Locale.ENGLISH);
        criteria.setRelation(RelationEnum.OWN);
        return new Object[][]{new Object[]{criteria}};
    }

    @DataProvider(name = "foreignAccountCriteria")
    public static Object[][] getTripForeignAccountCriteria() {
        EventForeignAccountCriteria criteria = new EventForeignAccountCriteria();
        criteria.setContentType(ContentEnum.EVENT);
        criteria.setAccountId(OWNER_ID);
        criteria.setUserId(USER_ID);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
        criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
        criteria.setTypes(new IDListCriteria(CheckModeEnum.IN, EVENT_TYPE_IDS));
        criteria.setSkip(0L);
        criteria.setLimit(10L);
        criteria.setSort(ContentSortEnum.CREATED);
        criteria.setLocale(Locale.ENGLISH);
        criteria.setRelation(RelationEnum.OWN);
        return new Object[][]{new Object[]{criteria}};
    }

}
