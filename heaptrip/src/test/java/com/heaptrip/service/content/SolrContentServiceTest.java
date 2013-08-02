package com.heaptrip.service.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.service.content.SolrContentService;
import com.heaptrip.domain.service.content.criteria.SearchContentResponse;
import com.heaptrip.domain.service.content.criteria.SolrContentCriteria;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class SolrContentServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SolrContentService solrContentService;

	@Test(priority = 1, enabled = true, dataProvider = "solrContentCriteria", dataProviderClass = ContentDataProvider.class)
	public void findContentsBySolrContentCriteria(SolrContentCriteria criteria) {
		// call
		SearchContentResponse response = solrContentService.findContentsBySolrContentCriteria(criteria);
		// check
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getNumFound() > 0);
		//Assert.assertNotNull(response.getContents());
		//Assert.assertTrue(response.getContents().size() > 0);
	}

}
