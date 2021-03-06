package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.ContentTextCriteria;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import org.apache.commons.lang.ArrayUtils;
import org.testng.annotations.DataProvider;

import java.util.Locale;

public class ContentDataProvider {

    public static String CONTENT_ID = "CONTENT_FOR_CONTENT_SERVICES_TESTS";

    public static String OWNER_ID = "OWNER_FOR_CONTENT_SERVICES_TESTS";

    public static String USER_ID = "USER_FOR_CONTENT_SERVICES_TESTS";

    public static String[] CATEGORY_IDS = new String[]{"2.4.7", "3.2"};

    public static String[] REGION_IDS = null;

    @DataProvider(name = "contentTextCriteria")
    public static Object[][] getContentTextCriteria() {
        ContentTextCriteria criteria = new ContentTextCriteria();
        criteria.setContentType(ContentEnum.POST);
        criteria.setUserId(USER_ID);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, CATEGORY_IDS));
        if (!ArrayUtils.isEmpty(REGION_IDS)) {
            criteria.setRegions(new IDListCriteria(CheckModeEnum.IN, REGION_IDS));
        }
        criteria.setSkip(0L);
        criteria.setLimit(10L);
        criteria.setLocale(Locale.ENGLISH);
        criteria.setQuery("name summary description");
        criteria.setTextLength(300L);
        return new Object[][]{new Object[]{criteria}};
    }
}
