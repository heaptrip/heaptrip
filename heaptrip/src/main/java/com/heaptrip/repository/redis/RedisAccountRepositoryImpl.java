package com.heaptrip.repository.redis;

import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.RedisContext;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisAccountRepositoryImpl implements RedisAccountRepository {

    @Autowired
    private RedisContext redisContext;

    @Override
    public void save(RedisAccount account) {
        Assert.notNull(account.getId(), "accountId must not be null");

        String key = "account:" +  account.getId();

        Map<String, String> values = new HashMap<>();
        if(account.getEmail() != null) {
            values.put("emails", account.getEmail());
        }
        if(account.getName() != null) {
            values.put("name", account.getName());
        }
        values.put("rating", Double.toString(account.getRating()));
        if(account.getImageId() != null) {
            values.put("imageId", account.getImageId());
        }
        if(account.getThumbnailId() != null) {
            values.put("thumbnailId", account.getThumbnailId());
        }

        Jedis jedis = redisContext.getJedis();
        jedis.hmset(key, values);
    }

    @Override
    public void updateRating(String accountId, double ratingValue) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" +  accountId;

        Jedis jedis = redisContext.getJedis();
        jedis.hset(key, "rating", Double.toString(ratingValue));
    }

    @Override
    public void remove(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" +  accountId;

        Jedis jedis = redisContext.getJedis();
        jedis.del(key);
    }

    @Override
    public boolean exists(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" +  accountId;

        Jedis jedis = redisContext.getJedis();
        return jedis.exists(key);
    }

    @Override
    public RedisAccount findOne(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" +  accountId;

        String[] fields = new String[]{"emails", "name", "rating", "imageId", "thumbnailId"};

        Jedis jedis = redisContext.getJedis();
        List<String> values = jedis.hmget(key, fields);

        RedisAccount result = new RedisAccount();
        result.setId(accountId);
        if(values.get(0) != null) {
            result.setEmail(values.get(0));
        }
        if(values.get(1) != null) {
            result.setName(values.get(1));
        }
        if(values.get(2) != null) {
            result.setRating(Double.parseDouble(values.get(2)));
        }
        if(values.get(3) != null) {
            result.setImageId(values.get(3));
        }
        if(values.get(4) != null) {
            result.setThumbnailId(values.get(4));
        }

        return result;
    }
}


