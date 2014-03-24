package com.heaptrip.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ImageServiceTest extends AbstractTestNGSpringContextTests {

    private static final String TARGET_ID = "1";

    private static final String OWNER_ID = "1";

    private static final String IMAGE_NAME = "penguins.jpg";

    private static final ImageEnum IMAGE_TYPE = ImageEnum.TRIP_ALBUM_IMAGE;

    @Autowired
    @Qualifier(ImageService.SERVICE_NAME)
    private ImageService imageService;

    @Autowired
    private ResourceLoader loader;

    private Image image;

    @BeforeClass
    public void init() {
        imageService.removeImagesByTargetId(TARGET_ID);
    }

    @Test(enabled = true, priority = 0)
    public void addAlbumImage() throws IOException {
        // call
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        Image image = imageService.addImage(TARGET_ID, OWNER_ID, IMAGE_TYPE, IMAGE_NAME, is);
        // check
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertEquals(image.getOwnerId(), OWNER_ID);
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
    }

    @Test(enabled = true, priority = 1)
    public void getAlbumImages() throws IOException {
        // call
        List<Image> images = imageService.getImagesByTargetId(TARGET_ID);
        // check
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
        image = images.get(0);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getOwnerId());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        // call
        images = imageService.getImagesByTargetId(TARGET_ID, 0, 1);
        // check
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
    }

    @Test(enabled = true, priority = 2)
    public void getAlbumImage() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        image = imageService.getImageById(image.getId());
        // check
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getOwnerId());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
    }

    @Test(enabled = true, priority = 3)
    public void updateAlbumImage() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        String name = "test name";
        String text = "test text";
        image.setName(name);
        image.setText(text);
        imageService.updateImage(image);
        // check
        image = imageService.getImageById(image.getId());
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getName(), name);
        Assert.assertEquals(image.getText(), text);
    }

    @Test(enabled = true, priority = 4)
    public void like() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        Assert.assertNull(image.getLikes());
        imageService.like(image.getId());
        // check
        image = imageService.getImageById(image.getId());
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getLikes());
        Assert.assertEquals(image.getLikes(), new Long(1));
    }

    @Test(enabled = true, priority = 5)
    public void removeAlbumImage() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        imageService.removeImageById(image.getId());
        // check
        image = imageService.getImageById(image.getId());
        Assert.assertNull(image);
    }

}
