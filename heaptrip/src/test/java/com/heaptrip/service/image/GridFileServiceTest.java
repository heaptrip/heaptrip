package com.heaptrip.service.image;

import com.heaptrip.domain.service.image.GridFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class GridFileServiceTest extends AbstractTestNGSpringContextTests {

    private static final String IMAGE_NAME = "penguins.jpg";

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ResourceLoader loader;

    private String fileId;

    @Test(enabled = true, priority = 0)
    public void saveImage() throws IOException {
        // call
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        fileId = gridFileService.saveFile(file.getName(), is);
        // check
        Assert.assertNotNull(fileId);
    }

    @Test(enabled = true, priority = 1)
    public void getImage() {
        // call
        Assert.assertNotNull(fileId);
        InputStream is = gridFileService.getFile(fileId);
        // check
        Assert.assertNotNull(is);
    }

    @Test(enabled = true, priority = 2)
    public void removeImage() {
        // call
        Assert.assertNotNull(fileId);
        gridFileService.removeFile(fileId);
        // check
        InputStream is = gridFileService.getFile(fileId);
        Assert.assertNull(is);
    }

}
