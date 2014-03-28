package com.heaptrip.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.image.criteria.ImageCriteria;
import com.heaptrip.security.Authenticate;
import com.heaptrip.security.AuthenticationListener;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@Listeners(AuthenticationListener.class)
public class ImageServiceTest extends AbstractTestNGSpringContextTests {

    private static final String TARGET_ID = "targetId4ImageServiceTest";

    private static final String OWNER_ID = "ownerId4ImageServiceTest";

    private static final String IMAGE_NAME = "penguins.jpg";

    @Autowired
    private ImageService imageService;

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ResourceLoader loader;

    private Image image;

    @BeforeMethod
    public void beforeMethod() throws IOException {
        image = imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());
    }

    @BeforeClass
    @AfterMethod
    public void afterMethod() {
        imageService.removeImagesByTargetId(TARGET_ID);
        image = null;
    }

    private InputStream testInputStream() throws IOException {
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        return new FileInputStream(file);
    }

    @Authenticate(userid = OWNER_ID)
    @Test(enabled = true)
    public void addImageWithAuthenticate() throws IOException {
        // call
        Image image = imageService.addImage(TARGET_ID, ImageEnum.TRIP_ALBUM_IMAGE, IMAGE_NAME, testInputStream());
        // check
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertEquals(image.getOwnerId(), OWNER_ID);
        Assert.assertEquals(image.getType(), ImageEnum.TRIP_ALBUM_IMAGE);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNotNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true)
    public void addImageWithoutAuthenticate() throws IOException {
        // call
        image = imageService.addImage(TARGET_ID, ImageEnum.ACCOUNT_IMAGE, IMAGE_NAME, testInputStream());
        // check
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getType(), ImageEnum.ACCOUNT_IMAGE);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNotNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true)
    public void updateNameAndText() throws IOException {
        // prepare
        Assert.assertNotNull(image);
        String imageId = image.getId();
        // call
        String name = "test name";
        String text = "test text";
        imageService.updateNameAndText(imageId, name, text);
        // check
        image = imageService.getImageById(imageId);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getName(), name);
        Assert.assertEquals(image.getText(), text);
    }

    @Test(enabled = true)
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

    @Test(enabled = true)
    public void getImageById() throws IOException {
        // call
        Assert.assertNotNull(image);
        Assert.assertNotNull(image.getId());
        image = imageService.getImageById(image.getId());
        // check
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getType(), ImageEnum.CONTENT_IMAGE);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true)
    public void getImagesByCriteriaWithTargetId() throws IOException {
        // call
        ImageCriteria imageCriteria = new ImageCriteria();
        imageCriteria.setTargetId(TARGET_ID);
        List<Image> images = imageService.getImagesByCriteria(imageCriteria);
        long count = imageService.getCountByCriteria(imageCriteria);
        // check
        Assert.assertEquals(count, 1);
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
        image = images.get(0);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getType(), ImageEnum.CONTENT_IMAGE);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true)
    public void getImagesByCriteriaWithTargetIdAndImageType() throws IOException {
        // call
        ImageCriteria imageCriteria = new ImageCriteria();
        imageCriteria.setTargetId(TARGET_ID);
        imageCriteria.setImageType(ImageEnum.CONTENT_IMAGE);
        List<Image> images = imageService.getImagesByCriteria(imageCriteria);
        long count = imageService.getCountByCriteria(imageCriteria);
        // check
        Assert.assertEquals(count, 1);
        Assert.assertNotNull(images);
        Assert.assertEquals(images.size(), 1);
        image = images.get(0);
        Assert.assertNotNull(image);
        Assert.assertEquals(image.getTarget(), TARGET_ID);
        Assert.assertNull(image.getOwnerId());
        Assert.assertEquals(image.getType(), ImageEnum.CONTENT_IMAGE);
        Assert.assertEquals(image.getName(), IMAGE_NAME);
        Assert.assertNull(image.getText());
        Assert.assertNotNull(image.getRefs());
        Assert.assertNull(image.getRefs().getSmall());
        Assert.assertNotNull(image.getRefs().getMedium());
        Assert.assertNull(image.getRefs().getFull());
        Assert.assertNotNull(image.getUploaded());
        Assert.assertNull(image.getLikes());
    }

    @Test(enabled = true)
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

    @Test(enabled = true)
    public void removeImagesByIds() throws IOException {
        // prepare images
        imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());
        imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());

        ImageCriteria imageCriteria = new ImageCriteria();
        imageCriteria.setTargetId(TARGET_ID);
        List<Image> images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(!CollectionUtils.isEmpty(images));

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
        images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(CollectionUtils.isEmpty(images));
    }

    @Test(enabled = true)
    public void removeImagesByTargetIdAndImageType() throws IOException {
        // prepare images
        imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());
        imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());

        ImageCriteria imageCriteria = new ImageCriteria();
        imageCriteria.setTargetId(TARGET_ID);
        imageCriteria.setImageType(ImageEnum.CONTENT_IMAGE);

        List<Image> images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(!CollectionUtils.isEmpty(images));

        // call
        imageService.removeImagesByTargetId(TARGET_ID, ImageEnum.CONTENT_IMAGE);
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
        images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(CollectionUtils.isEmpty(images));
    }

    @Test(enabled = true)
    public void removeImagesByTargetId() throws IOException {
        // prepare images
        imageService.addImage(TARGET_ID, ImageEnum.CONTENT_IMAGE, IMAGE_NAME, testInputStream());
        imageService.addImage(TARGET_ID, ImageEnum.ACCOUNT_IMAGE, IMAGE_NAME, testInputStream());

        ImageCriteria imageCriteria = new ImageCriteria();
        imageCriteria.setTargetId(TARGET_ID);

        List<Image> images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(!CollectionUtils.isEmpty(images));

        // call
        imageService.removeImagesByTargetId(TARGET_ID);
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
        images = imageService.getImagesByCriteria(imageCriteria);
        Assert.assertTrue(CollectionUtils.isEmpty(images));
    }
}
