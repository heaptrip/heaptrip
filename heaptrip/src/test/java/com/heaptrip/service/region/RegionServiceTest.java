package com.heaptrip.service.region;

import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.service.region.RegionService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RegionServiceTest extends AbstractTestNGSpringContextTests {

    private Locale locale = Locale.ENGLISH;

    @Autowired
    private RegionService regionService;

    private List<Region> regions;

    @Test(enabled = true, priority = 1)
    public void getRegionsByName() throws SolrServerException {
        // call
        String name = "Russia Ukraine Belarus";
        regions = regionService.getRegionsByName(name, 0L, 10L, locale);
        // check
        Assert.assertNotNull(regions);
        Assert.assertTrue(regions.size() > 0);
        for (Region region : regions) {
            Assert.assertNotNull(region);
            Assert.assertNotNull(region.getId());
            Assert.assertNotNull(region.getName());
            Assert.assertNotNull(region.getName().getValue(locale));
            Assert.assertNotNull(region.getPath());
            Assert.assertNotNull(region.getPath().getValue(locale));
            Assert.assertNotNull(region.getType());
        }
    }

    @Test(enabled = true, priority = 2)
    public void getRegionById() throws SolrServerException {
        for (Region region : regions) {
            // call
            Region persist = regionService.getRegionById(region.getId(), locale);
            // check
            Assert.assertNotNull(persist);
            Assert.assertEquals(persist, region);
        }
    }
}
