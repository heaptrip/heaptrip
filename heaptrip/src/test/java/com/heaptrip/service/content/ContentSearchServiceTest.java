package com.heaptrip.service.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.criteria.ContentSearchResponse;
import com.heaptrip.domain.service.content.criteria.ContentTextCriteria;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentSearchServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private ContentSearchService contentSearchService;

	@Test(priority = 0, enabled = true, dataProviderClass = ContentDataProvider.class, dataProvider = "contentTextCriteria")
	public void findContentsByTextCriteria(ContentTextCriteria criteria) {
		// call
		ContentSearchResponse response = contentSearchService.findContentsByTextCriteria(criteria);
		// check
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getNumFound() > 0);
		Assert.assertNotNull(response.getObjects());
		Assert.assertTrue(response.getObjects().size() > 0);
		for (Content content : response.getObjects()) {
			Assert.assertNotNull(content.getName());
			Assert.assertNotNull(content.getName().getValue(criteria.getLocale()));
			Assert.assertNotNull(content.getSummary());
			Assert.assertNotNull(content.getSummary().getValue(criteria.getLocale()));
		}
	}

}
