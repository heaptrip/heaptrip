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

        String key = "account:" + account.getId();

        Map<String, String> values = new HashMap<>();
        if (account.getEmail() != null) {
            values.put("emails", account.getEmail());
        }
        if (account.getName() != null) {
            values.put("name", account.getName());
        }
        values.put("rating", Double.toString(account.getRating()));
        if (account.getImageId() != null) {
            values.put("imageId", account.getImageId());
        }
        if (account.getThumbnailId() != null) {
            values.put("thumbnailId", account.getThumbnailId());
        }

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.hmset(key, values);
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }
    }

    @Override
    public void updateRating(String accountId, double ratingValue) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.hset(key, "rating", Double.toString(ratingValue));
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }
    }

    @Override
    public void updateImages(String accountId, String imageId, String thumbnailId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.hset(key, "imageId", imageId);
            jedis.hset(key, "thumbnailId", thumbnailId);
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }
    }

    @Override
    public void remove(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.del(key);
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }
    }

    @Override
    public boolean exists(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }
    }

    @Override
    public RedisAccount findOne(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        String[] fields = new String[]{"emails", "name", "rating", "imageId", "thumbnailId"};

        List<String> values = null;
        Jedis jedis = redisContext.getConnection();
        try {
            values = jedis.hmget(key, fields);
        } catch (Exception e) {
            if (jedis != null) {
                redisContext.returnBrokenConnection(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (jedis != null) {
                redisContext.returnConnection(jedis);
            }
        }

        RedisAccount result = new RedisAccount();
        result.setId(accountId);
        if (values.get(0) != null) {
            result.setEmail(values.get(0));
        }
        if (values.get(1) != null) {
            result.setName(values.get(1));
        }
        if (values.get(2) != null) {
            result.setRating(Double.parseDouble(values.get(2)));
        }
        if (values.get(3) != null) {
            result.setImageId(values.get(3));
        }
        if (values.get(4) != null) {
            result.setThumbnailId(values.get(4));
        }

        return result;
    }
}


