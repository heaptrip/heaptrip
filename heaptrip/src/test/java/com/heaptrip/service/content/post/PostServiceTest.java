package com.heaptrip.service.content.post;

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.repository.content.post.PostRepository;
import com.heaptrip.domain.service.content.post.PostService;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class PostServiceTest extends AbstractTestNGSpringContextTests {

	private static final String POST_ID = "POST_FOR_POST_SERVICE_TEST";

	private static final String OWNER_ID = "OWNER_FOR_POST_SERVICE_TEST";

	private static final Locale locale = LanguageUtils.getEnglishLocale();

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	private Post post = null;

	@BeforeClass
	public void init() {
		post = new Post();
		post.setId(POST_ID);
		ContentOwner owner = new ContentOwner();
		owner.setId(OWNER_ID);
		post.setOwner(owner);
	}

	@AfterClass(alwaysRun = true)
	public void relese() {
		postService.hardRemove(POST_ID);
	}

	@Test(priority = 0, enabled = false)
	public void save() {
		// call
		postService.save(post, LanguageUtils.getEnglishLocale());
		// check
		Post post = postRepository.findOne(POST_ID);
		Assert.assertNotNull(post);
		Assert.assertEquals(post, this.post);
		Assert.assertNotNull(post.getCreated());
		Assert.assertNull(post.getDeleted());
		Assert.assertTrue(ArrayUtils.isEmpty(post.getAllowed()));
		Assert.assertNotNull(post.getMainLang());
		Assert.assertEquals(post.getMainLang(), locale.getLanguage());
		Assert.assertTrue(ArrayUtils.isNotEmpty(post.getLangs()));
		Assert.assertEquals(post.getLangs()[0], locale.getLanguage());
		Assert.assertNotNull(post.getOwner());
		Assert.assertNotNull(post.getOwner().getId());
		Assert.assertEquals(post.getOwner().getId(), OWNER_ID);
		Assert.assertNotNull(post.getStatus());
		Assert.assertEquals(post.getStatus(), ContentStatusEnum.DRAFT);
		Assert.assertNotNull(post.getRating());
		Assert.assertEquals(post.getRating(), ContentRating.getDefaultValue());
		Assert.assertNotNull(post.getViews());
		Assert.assertEquals(post.getViews().getCount(), 0);
	}

	@Test(priority = 1, enabled = false)
	public void update() {
		// call
		Post post = postRepository.findOne(POST_ID);
		Assert.assertNotNull(post);
		String name = "Name for test post";
		String summary = "Summary for test post";
		String description = "Description for test post";
		Locale locale = new Locale(post.getMainLang());
		post.getName().setValue(name, locale);
		post.getSummary().setValue(summary, locale);
		post.getDescription().setValue(description, locale);
		postService.update(post);
		// check
		post = postRepository.findOne(POST_ID);
		Assert.assertNotNull(post);
		Assert.assertNotNull(post.getName());
		Assert.assertNotNull(post.getName().getValue(locale));
		Assert.assertEquals(post.getName().getValue(locale), name);
		Assert.assertNotNull(post.getSummary());
		Assert.assertNotNull(post.getSummary().getValue(locale));
		Assert.assertEquals(post.getSummary().getValue(locale), summary);
		Assert.assertNotNull(post.getDescription());
		Assert.assertNotNull(post.getDescription().getValue(locale));
		Assert.assertEquals(post.getDescription().getValue(locale), description);
	}
}
