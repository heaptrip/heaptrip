package com.heaptrip.repository.redis;

import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RedisAccountRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RedisAccountRepository redisAccountRepository;

    @BeforeClass
    public void beforeTest() throws Exception {
        this.springTestContextPrepareTestInstance();
        redisAccountRepository.remove(AccountDataProvider.ACCOUNT_ID);
    }

    @Test(priority = 1, enabled = true, dataProvider = "redisAccount", dataProviderClass = AccountDataProvider.class)
    public void save(RedisAccount account) {
        // call
        redisAccountRepository.save(account);
        // check
        Assert.assertTrue(redisAccountRepository.exists(account.getId()));
    }

    @Test(priority = 2, enabled = true, dataProvider = "redisAccount", dataProviderClass = AccountDataProvider.class)
    public void findOne(RedisAccount account) {
        // call
        RedisAccount readAccount = redisAccountRepository.findOne(account.getId());
        // check
        Assert.assertNotNull(readAccount);
        Assert.assertEquals(readAccount.getName(), account.getName());
        Assert.assertEquals(readAccount.getEmail(), account.getEmail());
        Assert.assertEquals(readAccount.getRating(), account.getRating());
        Assert.assertEquals(readAccount.getImageId(), account.getImageId());
        Assert.assertEquals(readAccount.getSmallId(), account.getSmallId());
        Assert.assertEquals(readAccount.getMediumId(), account.getMediumId());
        Assert.assertEquals(readAccount.getAccountType(), account.getAccountType());
    }

    @Test(priority = 3, enabled = true, dataProvider = "redisAccount", dataProviderClass = AccountDataProvider.class)
    public void updateRating(RedisAccount account) {
        // call
        double newRating = 100d;
        redisAccountRepository.updateRating(account.getId(), newRating);
        // check
        RedisAccount readAccount = redisAccountRepository.findOne(account.getId());
        Assert.assertNotNull(readAccount);
        Assert.assertEquals(readAccount.getRating(), newRating);
    }

    @Test(priority = 4, enabled = true, dataProvider = "redisAccount", dataProviderClass = AccountDataProvider.class)
    public void updateImage(RedisAccount account) {
        // prepare
        String newImageId = "11111";
        String newSmallImageId = "22222";
        String newMediumImageId = "33333";
        RedisAccount readAccount = redisAccountRepository.findOne(account.getId());
        Assert.assertNotNull(readAccount);
        Assert.assertNotEquals(readAccount.getImageId(), newImageId);
        Assert.assertNotEquals(readAccount.getSmallId(), newSmallImageId);
        Assert.assertNotEquals(readAccount.getMediumId(), newMediumImageId);
        // call
        redisAccountRepository.updateImages(account.getId(), newImageId, newSmallImageId, newMediumImageId);
        // check
        readAccount = redisAccountRepository.findOne(account.getId());
        Assert.assertNotNull(readAccount);
        Assert.assertEquals(readAccount.getImageId(), newImageId);
        Assert.assertEquals(readAccount.getSmallId(), newSmallImageId);
        Assert.assertEquals(readAccount.getMediumId(), newMediumImageId);
    }

    @Test(priority = 5, enabled = true, dataProvider = "redisAccount", dataProviderClass = AccountDataProvider.class)
    public void remove(RedisAccount account) throws SolrServerException, IOException {
        // call
        redisAccountRepository.remove(account.getId());
        // check
        Assert.assertFalse(redisAccountRepository.exists(account.getId()));
    }
}
