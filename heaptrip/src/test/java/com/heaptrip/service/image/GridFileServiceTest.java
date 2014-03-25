package com.heaptrip.service.image;

import com.heaptrip.domain.service.image.GridFileService;
import com.heaptrip.util.stream.StreamUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
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
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class GridFileServiceTest extends AbstractTestNGSpringContextTests {

    private static final String IMAGE_NAME = "penguins.jpg";

    @Autowired
    private GridFileService gridFileService;

    @Autowired
    private ResourceLoader loader;

    private String fileId;

    private byte[] CRC;

    @Test(enabled = true, priority = 0)
    public void saveFile() throws IOException {
        // call
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        fileId = gridFileService.saveFile(file.getName(), is);
        CRC = DigestUtils.md5(is);
        // check
        Assert.assertNotNull(fileId);
        Assert.assertNotNull(CRC);
    }

    @Test(enabled = true, priority = 1)
    public void getFile() throws IOException {
        // call
        Assert.assertNotNull(fileId);
        InputStream is = gridFileService.getFile(fileId);
        // check is
        Assert.assertNotNull(is);
        byte[] newCRC = DigestUtils.md5(is);
        Assert.assertNotNull(newCRC);
        ArrayUtils.isEquals(CRC, newCRC);
    }

    @Test(enabled = true, priority = 2)
    public void removeFile() {
        // call
        Assert.assertNotNull(fileId);
        gridFileService.removeFile(fileId);
        // check
        InputStream is = gridFileService.getFile(fileId);
        Assert.assertNull(is);
    }

    @Test(enabled = true, priority = 3)
    public void removeFiles() throws IOException {
        // prepare
        List<String> fileIds = new ArrayList<>();
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = StreamUtils.getResetableInputStream(new FileInputStream(file));
        fileIds.add(gridFileService.saveFile(file.getName(), is));
        is.reset();
        fileIds.add(gridFileService.saveFile(file.getName(), is));
        is.reset();
        fileIds.add(gridFileService.saveFile(file.getName(), is));
        Assert.assertEquals(fileIds.size(), 3);
        for (String fileId : fileIds) {
            is = gridFileService.getFile(fileId);
            Assert.assertNotNull(is);
        }
        // call
        gridFileService.removeFiles(fileIds);
        // check
        for (String fileId : fileIds) {
            is = gridFileService.getFile(fileId);
            Assert.assertNull(is);
        }
    }

}
