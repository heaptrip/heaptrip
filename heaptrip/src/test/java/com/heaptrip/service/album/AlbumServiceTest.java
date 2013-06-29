package com.heaptrip.service.album;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumImageEnum;
import com.heaptrip.domain.service.album.AlbumService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AlbumServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TARGET_ID = "1";

	private static final String OWNER_ID = "1";

	private static final String IMAGE_NAME = "penguins.jpg";

	private static final AlbumImageEnum IMAGE_TYPE = AlbumImageEnum.TRIP_ALBUM_IMAGE;

	@Autowired
	@Qualifier(AlbumService.SERVICE_NAME)
	private AlbumService albumService;

	@Autowired
	private ResourceLoader loader;

	private AlbumImage albumImage = null;

	@BeforeClass
	public void init() {
		albumService.removeAlbumImagesByTargetId(TARGET_ID);
	}

	@Test(enabled = true, priority = 0)
	public void addAlbumImage() throws IOException {
		// call
		Resource resource = loader.getResource(IMAGE_NAME);
		Assert.assertNotNull(resource);
		File file = resource.getFile();
		InputStream is = new FileInputStream(file);
		AlbumImage albumImage = albumService.addAlbumImage(TARGET_ID, OWNER_ID, IMAGE_TYPE, IMAGE_NAME, is);
		// check
		Assert.assertNotNull(albumImage);
		Assert.assertEquals(albumImage.getTarget(), TARGET_ID);
		Assert.assertNotNull(albumImage.getOwner());
		Assert.assertEquals(albumImage.getOwner().getId(), OWNER_ID);
		Assert.assertNotNull(albumImage.getRefs());
		Assert.assertNotNull(albumImage.getRefs().getSmall());
		Assert.assertNotNull(albumImage.getRefs().getMedium());
		Assert.assertNotNull(albumImage.getRefs().getFull());
		Assert.assertNotNull(albumImage.getUploaded());
	}

	@Test(enabled = true, priority = 1)
	public void getAlbumImagesByTargetId() throws IOException {
		// call
		List<AlbumImage> albumImages = albumService.getAlbumImagesByTargetId(TARGET_ID);
		// check
		Assert.assertNotNull(albumImages);
		Assert.assertEquals(albumImages.size(), 1);
		albumImage = albumImages.get(0);
		Assert.assertNotNull(albumImage);
		Assert.assertNull(albumImage.getName());
		Assert.assertNull(albumImage.getText());
		Assert.assertNotNull(albumImage.getOwner());
		Assert.assertNotNull(albumImage.getRefs());
		Assert.assertNotNull(albumImage.getRefs().getSmall());
		Assert.assertNull(albumImage.getRefs().getMedium());
		Assert.assertNull(albumImage.getRefs().getFull());
		// call
		albumImages = albumService.getAlbumImagesByTargetId(TARGET_ID, 1);
		// check
		Assert.assertNotNull(albumImages);
		Assert.assertEquals(albumImages.size(), 1);
	}

	@Test(enabled = true, priority = 2)
	public void getAlbumImageById() throws IOException {
		// call
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getId());
		albumImage = albumService.getAlbumImageById(albumImage.getId());
		// check
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getOwner());
		Assert.assertNotNull(albumImage.getRefs());
		Assert.assertNotNull(albumImage.getRefs().getSmall());
		Assert.assertNotNull(albumImage.getRefs().getMedium());
		Assert.assertNotNull(albumImage.getRefs().getFull());
	}

	@Test(enabled = true, priority = 3)
	public void updateAlbumImage() throws IOException {
		// call
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getId());
		String name = "test name";
		String text = "test text";
		albumImage.setName(name);
		albumImage.setText(text);
		albumService.updateAlbumImage(albumImage);
		// check
		albumImage = albumService.getAlbumImageById(albumImage.getId());
		Assert.assertNotNull(albumImage);
		Assert.assertEquals(albumImage.getName(), name);
		Assert.assertEquals(albumImage.getText(), text);
	}

	@Test(enabled = true, priority = 4)
	public void like() throws IOException {
		// call
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getId());
		Assert.assertNull(albumImage.getLikes());
		albumService.like(albumImage.getId());
		// check
		albumImage = albumService.getAlbumImageById(albumImage.getId());
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getLikes());
		Assert.assertEquals(albumImage.getLikes(), new Long(1));
	}

	@Test(enabled = true, priority = 5)
	public void removeAlbumImage() throws IOException {
		// call
		Assert.assertNotNull(albumImage);
		Assert.assertNotNull(albumImage.getId());
		albumService.removeAlbumImage(albumImage.getId());
		// check
		albumImage = albumService.getAlbumImageById(albumImage.getId());
		Assert.assertNull(albumImage);
	}

}
