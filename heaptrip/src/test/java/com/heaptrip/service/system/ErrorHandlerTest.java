package com.heaptrip.service.system;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.service.region.RegionService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ErrorHandlerTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private RegionService regionService;

	@Test(enabled = false, priority = 1)
	public void solrDisconnect() throws SolrServerException {
		// call
		String name = "Russia Ukraine Belarus";
		List<Region> regions = regionService.getRegionsByName(name, 0L, 10L, Locale.ENGLISH);
		// check
		Assert.assertNotNull(regions);
		Assert.assertTrue(regions.size() > 0);
	}

}
