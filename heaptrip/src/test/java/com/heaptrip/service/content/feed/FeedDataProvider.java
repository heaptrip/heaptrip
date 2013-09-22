package com.heaptrip.service.content.feed;

import java.util.Locale;

import org.testng.annotations.DataProvider;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;

public class FeedDataProvider {

	static String[] CATEGORY_IDS = new String[] { "2.4.7", "3.2" };

	static String[] REGION_IDS = new String[] { "1", "7", "8" };

	static String CONTENT_ID = "CONTENT_FOR_CONTENT_FEED_SERVICE_TEST";

	static String OWNER_ID = "OWNER_FOR_CONTENT_FEED_SERVICE_TEST";

	static String USER_ID = "USER_FOR_CONTENT_FEED_SERVICE_TEST";

	@DataProvider(name = "feedCriteria")
	public static Object[][] getFeedCriteria() {
		FeedCriteria criteria = new FeedCriteria();
		criteria.setUserId(USER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.CREATED);
		criteria.setLocale(Locale.ENGLISH);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "myAccountCriteria")
	public static Object[][] getMyAccountCriteria() {
		MyAccountCriteria criteria = new MyAccountCriteria();
		criteria.setContentType(ContentEnum.POST);
		criteria.setUserId(OWNER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.RATING);
		criteria.setStatus(new ContentStatusEnum[] { ContentStatusEnum.PUBLISHED_FRIENDS });
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.OWN);
		return new Object[][] { new Object[] { criteria } };
	}

	@DataProvider(name = "foreignAccountCriteria")
	public static Object[][] getTripForeignAccountCriteria() {
		ForeignAccountCriteria criteria = new ForeignAccountCriteria();
		criteria.setContentType(ContentEnum.POST);
		criteria.setAccountId(OWNER_ID);
		criteria.setUserId(USER_ID);
		criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
		criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
		criteria.setSkip(0L);
		criteria.setLimit(10L);
		criteria.setSort(ContentSortEnum.CREATED);
		criteria.setLocale(Locale.ENGLISH);
		criteria.setRelation(RelationEnum.OWN);
		return new Object[][] { new Object[] { criteria } };
	}
}
