package com.heaptrip.repository.redis;

import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import org.testng.annotations.DataProvider;

public class AccountDataProvider {

    static final String ACCOUNT_ID = RedisAccountRepositoryTest.class.getName();

    @DataProvider(name = "redisAccount")
    public static Object[][] getSolrAccount() {
        RedisAccount account = new RedisAccount();
        account.setId(ACCOUNT_ID);
        account.setName("Иванов Петр Сергеевич");
        account.setEmail("test@test.com");
        account.setRating(1d);
        account.setImageId("111");
        account.setSmallId("222");
        account.setMediumId("333");
        return new Object[][]{new Object[]{account}};
    }
}
