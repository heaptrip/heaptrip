package com.heaptrip.service.content;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ContentServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier(ContentService.SERVICE_NAME)
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Test(priority = 0, enabled = true)
	public void setStatus() {
		// call
		Content content = contentRepository.findOne(ContentDataProvider.CONTENT_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.DRAFT);
		contentService.setStatus(ContentDataProvider.CONTENT_ID, ContentStatusEnum.PUBLISHED_ALL);
		// check
		content = contentRepository.findOne(ContentDataProvider.CONTENT_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getStatus());
		Assert.assertNotNull(content.getStatus().getValue());
		Assert.assertEquals(content.getStatus().getValue(), ContentStatusEnum.PUBLISHED_ALL);
	}

	@Test(priority = 1, enabled = true)
	public void incViews() throws InterruptedException, ExecutionException {
		String[] remoteHostIp = new String[] { "127.0.0.1", "192.168.1.1" };
		Content content = contentRepository.findOne(ContentDataProvider.CONTENT_ID);
		Assert.assertNotNull(content);
		Assert.assertTrue(content.getViews() == null || content.getViews().getCount() == 0);
		// call
		Future<Void> views = contentService.incViews(ContentDataProvider.CONTENT_ID, remoteHostIp[0]);
		views.get();
		views = contentService.incViews(ContentDataProvider.CONTENT_ID, remoteHostIp[1]);
		views.get();
		views = contentService.incViews(ContentDataProvider.CONTENT_ID, remoteHostIp[0]);
		views.get();
		views = contentService.incViews(ContentDataProvider.CONTENT_ID, remoteHostIp[1]);
		views.get();
		// check
		content = contentRepository.findOne(ContentDataProvider.CONTENT_ID);
		Assert.assertNotNull(content);
		Assert.assertNotNull(content.getViews());
		Assert.assertNotNull(content.getViews().getCount());
		Assert.assertEquals(content.getViews().getCount(), 2);
	}

	@Test(priority = 2, enabled = true)
	public void isOwner() {
		// check userId
		boolean isOwner = contentService.isOwner(ContentDataProvider.CONTENT_ID, ContentDataProvider.USER_ID);
		Assert.assertFalse(isOwner);
		// check ownerId
		isOwner = contentService.isOwner(ContentDataProvider.CONTENT_ID, ContentDataProvider.OWNER_ID);
		Assert.assertTrue(isOwner);
		// add userId to owners list and check it
		Content post = contentRepository.findOne(ContentDataProvider.CONTENT_ID);
		post.setOwners(new String[] { ContentDataProvider.USER_ID });
		contentRepository.save(post);
		isOwner = contentService.isOwner(ContentDataProvider.CONTENT_ID, ContentDataProvider.USER_ID);
		Assert.assertTrue(isOwner);
	}
}
