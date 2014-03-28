package com.heaptrip.service.account.store;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class ChangeAccountImageTest extends AbstractTestNGSpringContextTests {

    private static final String IMAGE_NAME = "penguins.jpg";

    private static final String USER_ID = "userId4ChangeAccountImageTest";

    @Autowired
    private ImageService imageService;

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private RedisAccountRepository redisAccountRepository;

    private Image image;

    @BeforeClass
    public void beforeTest() throws Exception {
        this.springTestContextPrepareTestInstance();
        redisAccountRepository.remove(USER_ID);
        Resource resource = loader.getResource(IMAGE_NAME);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);
        image = imageService.addImage(USER_ID, ImageEnum.ACCOUNT_IMAGE, ChangeAccountImageTest.class.getSimpleName(), is);
        Account user = new User();
        user.setId(USER_ID);
        accountRepository.save(user);
    }

    @AfterClass
    public void afterTest() {
        if (image != null) {
            imageService.removeImageById(image.getId());
        }
        accountRepository.remove(USER_ID);
    }

    @Test(enabled = true, priority = 0)
    public void changeImage() throws IOException, ExecutionException, InterruptedException {
        // prepare
        Account user = accountStoreService.findOne(USER_ID);
        Assert.assertNotNull(user);
        Assert.assertNull(user.getImage());
        // call
        Future<Void> res = accountStoreService.changeImage(USER_ID, image);
        res.get();
        // check
        user = accountStoreService.findOne(USER_ID);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getImage());
        Assert.assertEquals(user.getImage().getId(), image.getId());
        Assert.assertNotNull(user.getImage().getRefs());
        Assert.assertEquals(user.getImage().getRefs().getSmall(), image.getRefs().getSmall());
        Assert.assertEquals(user.getImage().getRefs().getMedium(), image.getRefs().getMedium());
    }
}
