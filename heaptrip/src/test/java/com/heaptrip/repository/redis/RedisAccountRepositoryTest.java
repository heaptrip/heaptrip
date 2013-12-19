package com.heaptrip.repository.redis;

import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class RedisAccountRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RedisAccountRepository redisAccountRepository;

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
        Assert.assertEquals(readAccount.getThumbnailId(), account.getThumbnailId());
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
    public void remove(RedisAccount account) throws SolrServerException, IOException {
        // call
        redisAccountRepository.remove(account.getId());
        // check
        Assert.assertFalse(redisAccountRepository.exists(account.getId()));
    }
}
