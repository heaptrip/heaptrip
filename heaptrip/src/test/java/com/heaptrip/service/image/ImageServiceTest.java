package com.heaptrip.service.image;

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

import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.ImageService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ImageServiceTest extends AbstractTestNGSpringContextTests {

	private static final String IMAGE_NAME = "penguins.jpg";

	@Autowired
	private ImageService imageService;

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
		imageId = imageService.saveImage(file.getName(), ImageEnum.CONTENT_TITLE_IMAGE, is);
		// check
		Assert.assertNotNull(imageId);
	}

	@Test(enabled = true, priority = 1)
	public void getImage() {
		// call
		Assert.assertNotNull(imageId);
		InputStream is = imageService.getImage(imageId);
		// check
		Assert.assertNotNull(is);
	}

	@Test(enabled = true, priority = 2)
	public void removeImage() {
		// call
		Assert.assertNotNull(imageId);
		imageService.removeImage(imageId);
		// check
		InputStream is = imageService.getImage(imageId);
		Assert.assertNull(is);
	}

}
