package com.heaptrip.service;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.service.RegionService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RegionServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private RegionService regionService;

	@Test(enabled = false)
	public void getRegionsByName() throws SolrServerException {
		String name = "Moscow";
		Locale locale = Locale.ENGLISH;
		List<Region> regions = regionService.getRegionsByName(name, 0L, 10L, locale);
		Assert.assertNotNull(regions);
		Assert.assertTrue(regions.size() > 0);
		Assert.assertNotNull(regions.get(0));
		Region region = regions.get(0);
		Assert.assertNotNull(region.getName());
		Assert.assertNotNull(region.getName().getValue(locale));
		Assert.assertNotNull(region.getPath());
		Assert.assertNotNull(region.getPath().getValue(locale));
		Assert.assertNotNull(region.getType());
	}
}
