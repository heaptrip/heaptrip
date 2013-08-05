package com.heaptrip.service.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.criteria.SearchContentResponse;
import com.heaptrip.domain.service.content.criteria.СontextSearchCriteria;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentSearchServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ContentSearchService contentSearchService;

	@Test(priority = 1, enabled = true, dataProvider = "contextSearchCriteria", dataProviderClass = ContentDataProvider.class)
	public void findContentsByСontextSearchCriteria(СontextSearchCriteria criteria) {
		// call
		SearchContentResponse response = contentSearchService.findContentsByСontextSearchCriteria(criteria);
		// check
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getNumFound() > 0);
		Assert.assertNotNull(response.getContents());
		Assert.assertTrue(response.getContents().size() > 0);
		for (Content content : response.getContents()) {
			Assert.assertNotNull(content.getName());
			Assert.assertNotNull(content.getName().getValue(criteria.getLocale()));
			Assert.assertNotNull(content.getSummary());
			Assert.assertNotNull(content.getSummary().getValue(criteria.getLocale()));
		}
	}

}
