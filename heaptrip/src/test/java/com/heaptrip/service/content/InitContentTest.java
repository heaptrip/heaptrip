package com.heaptrip.service.content;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitContentTest extends AbstractTestNGSpringContextTests {

    private static String REGION_NAME = "Izhevsk";

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private SolrContentRepository solrContentRepository;

    @Autowired
    private RegionService regionService;

    private Post post = null;

    private SimpleCategory[] getCategories() {
        return new SimpleCategory[]{new SimpleCategory(ContentDataProvider.CATEGORY_IDS[0]),
                new SimpleCategory(ContentDataProvider.CATEGORY_IDS[1])};
    }

    private SimpleRegion[] getRegions() throws SolrServerException {
        SimpleRegion[] simpleRegions = null;
        List<Region> regions = regionService.getRegionsByName(REGION_NAME, 0L, 10L, LanguageUtils.getEnglishLocale());
        if (regions != null) {
            ContentDataProvider.REGION_IDS = new String[regions.size()];
            simpleRegions = new SimpleRegion[regions.size()];
            for (int i = 0; i < regions.size(); i++) {
                Region region = regions.get(i);
                ContentDataProvider.REGION_IDS[i] = region.getId();
                SimpleRegion simpleRegion = new SimpleRegion();
                simpleRegion.setId(region.getId());
                simpleRegion.setName(region.getName());
                simpleRegions[i] = simpleRegion;
            }
        }
        return simpleRegions;
    }

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();
        post = new Post();
        post.setId(ContentDataProvider.CONTENT_ID);
        post.setOwner(new ContentOwner(ContentDataProvider.OWNER_ID));
        post.setName(new MultiLangText("test name", LanguageUtils.getEnglishLocale()));
        post.setSummary(new MultiLangText("test summary", LanguageUtils.getEnglishLocale()));
        post.setDescription(new MultiLangText("test description", LanguageUtils.getEnglishLocale()));
        post.setCategories(getCategories());
        post.setCategoryIds(ContentDataProvider.CATEGORY_IDS);
        post.setRegions(getRegions());
        post.setRegionIds(ContentDataProvider.REGION_IDS);
        post.setAllowed(new String[]{ContentDataProvider.USER_ID});
        post.setLangs(new String[]{LanguageUtils.getEnglishLocale().getLanguage()});
        post.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
        contentRepository.save(post);
        solrContentRepository.save(post);
        solrContentRepository.commit();
    }

    @AfterTest
    public void afterTest() throws SolrServerException, IOException {
        contentRepository.remove(ContentDataProvider.CONTENT_ID);
        solrContentRepository.remove(ContentDataProvider.CONTENT_ID);
        solrContentRepository.commit();
    }
}
