package com.heaptrip.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.util.stream.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ImageServiceTest extends AbstractTestNGSpringContextTests {

    private static final String TARGET_ID = "1";

    private static final String OWNER_ID = "1";

    private static final String IMAGE_NAME = "penguins.jpg";

    private static final ImageEnum TRIP_ALBUM_IMAGE = ImageEnum.TRIP_ALBUM_IMAGE;

    private static final ImageEnum ACCOUNT_IMAGE = ImageEnum.ACCOUNT_IMAGE;

    private static final ImageEnum CONTENT_IMAGE = ImageEnum.CONTENT_IMAGE;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ResourceLoader loader;

    private Image image;

    @BeforeClass
    public void init() {
        imageService.removeImagesByTargetId(TARGET_ID);
    }

    @Test(enabled = true, priority = 0)
    public void addImageWithTargetIdAndOwnerId() throws IOException {
        // call
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        image = imageService.addImage(TARGET_ID, OWNER_ID, TRIP_ALBUM_IMAGE, IMAGE_NAME, is);
        // check
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertEquals(image.getOwnerId(), OWNER_ID);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true, priority = 1)
    public void getImagesByTargetId() throws IOException {
        // call
        List<Image> images = imageService.getImagesByTargetId(TARGET_ID);
        // check
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
        image = images.get(0);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertEquals(image.getOwnerId(), OWNER_ID);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true, priority = 2)
    public void getCountByTargetId() throws IOException {
        // call
        long count = imageService.getCountByTargetId(TARGET_ID);
        // check
        Assert.assertEquals(count, 1L);
    }

    @Test(enabled = true, priority = 3)
    public void getImagesByTargetIdWithSkipAndLimit() throws IOException {
        // call
        List<Image> images = imageService.getImagesByTargetId(TARGET_ID, 0, 1);
        // check
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
        image = images.get(0);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertEquals(image.getOwnerId(), OWNER_ID);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true, priority = 4)
    public void removeImagesByTargetId() {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getTarget());
        imageService.removeImagesByTargetId(image.getTarget());
        // check remove images from collection
        List<Image> images = imageService.getImagesByTargetId(TARGET_ID);
        Assert.assertTrue(CollectionUtils.isEmpty(images));
        // check remove images from GridFS
        if (image.getRefs() != null) {
            if (image.getRefs().getSmall() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getSmall()));
            }
            if (image.getRefs().getMedium() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getMedium()));
            }
            if (image.getRefs().getFull() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getFull()));
            }
        }
    }

    @Test(enabled = true, priority = 5)
    public void addImageWithoutTargetIdAndOwnerId() throws IOException {
        // call
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        image = imageService.addImage(ACCOUNT_IMAGE, IMAGE_NAME, is);
        // check
        Assert.assertNotNull(image);
        Assert.assertNull(image.getTarget());
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true, priority = 6)
    public void getImageById() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        image = imageService.getImageById(image.getId());
        // check
        Assert.assertNotNull(image);
        Assert.assertNull(image.getTarget());
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }


    @Test(enabled = true, priority = 7)
    public void updateImageNameAndText() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        String name = "test name";
        String text = "test text";
        image.setName(name);
        image.setText(text);
        imageService.updateImageNameAndText(image.getId(), name, text);
        // check
        image = imageService.getImageById(image.getId());
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getName(), name);
        Assert.assertEquals(image.getText(), text);
    }

    @Test(enabled = true, priority = 8)
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

    @Test(enabled = true, priority = 9)
    public void removeImageById() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        imageService.removeImageById(image.getId());
        // check remove images from GridFS
        if (image.getRefs() != null) {
            if (image.getRefs().getSmall() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getSmall()));
            }
            if (image.getRefs().getMedium() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getMedium()));
            }
            if (image.getRefs().getFull() != null) {
                Assert.assertNull(gridFileService.getFile(image.getRefs().getFull()));
            }
        }
        // check remove image from collection
        image = imageService.getImageById(image.getId());
        Assert.assertNull(image);
    }

    @Test(enabled = true, priority = 10)
    public void removeImagesByIds() throws IOException {
        // prepare images
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = StreamUtils.getResetableInputStream(new FileInputStream(file));
        List<Image> images = new ArrayList<>();
        images.add(imageService.addImage(TARGET_ID, OWNER_ID, CONTENT_IMAGE, IMAGE_NAME, is));
        is.reset();
        images.add(imageService.addImage(TARGET_ID, OWNER_ID, CONTENT_IMAGE, IMAGE_NAME, is));
        long count = imageService.getCountByTargetId(TARGET_ID);
        Assert.assertEquals(count, images.size());
        List<String> imageIds = new ArrayList<>();
        for (Image image : images) {
            imageIds.add(image.getId());
        }
        // call
        imageService.removeImagesByIds(imageIds);
        // check remove images from GridFS
        for (Image image : images) {
            if (image.getRefs() != null) {
                if (image.getRefs().getSmall() != null) {
                    Assert.assertNull(gridFileService.getFile(image.getRefs().getSmall()));
                }
                if (image.getRefs().getMedium() != null) {
                    Assert.assertNull(gridFileService.getFile(image.getRefs().getMedium()));
                }
                if (image.getRefs().getFull() != null) {
                    Assert.assertNull(gridFileService.getFile(image.getRefs().getFull()));
                }
            }
        }
        // check remove images from collection
        images = imageService.getImagesByTargetId(TARGET_ID);
        Assert.assertTrue(CollectionUtils.isEmpty(images));
    }
}
