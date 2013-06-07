package com.heaptrip.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.ImageEnum;
import com.heaptrip.domain.service.ImageStorageService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ImageStorageServiceTest extends AbstractTestNGSpringContextTests {

	private static final String IMAGE_NAME = "penguins.jpg";

	@Autowired
	private ImageStorageService imageStorageService;

	@Autowired
	private ResourceLoader loader;

	private String imageId = null;

	@Test(enabled = true, priority = 0)
	public void saveImage() throws IOException {
		// call
		Resource resource = loader.getResource(IMAGE_NAME);
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		imageId = imageStorageService.saveImage(file.getName(), ImageEnum.TRIP_IMAGE, is);
		// check
		Assert.assertNotNull(imageId);
	}

	@Test(enabled = true, priority = 1)
	public void getImage() {
		// call
		Assert.assertNotNull(imageId);
		InputStream is = imageStorageService.getImage(imageId);
		// check
		Assert.assertNotNull(is);
	}

	@Test(enabled = true, priority = 2)
	public void removeImage() {
		// call
		Assert.assertNotNull(imageId);
		imageStorageService.removeImage(imageId);
		// check
		InputStream is = imageStorageService.getImage(imageId);
		Assert.assertNull(is);
	}

}
