package com.heaptrip.repository.redis;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.RedisContext;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import org.apache.commons.lang.StringUtils;
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
        if (account.getSmallId() != null) {
            values.put("smallId", account.getSmallId());
        }
        if (account.getMediumId() != null) {
            values.put("mediumId", account.getMediumId());
        }
        if (account.getAccountType() != null) {
            values.put("accountType", account.getAccountType().toString());
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
    public void updateName(String accountId, String name) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.hset(key, "name", name);
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
    public void updateImages(String accountId, String imageId, String smallId, String mediumId) {
        Assert.notNull(accountId, "accountId must not be null");

        String key = "account:" + accountId;

        Jedis jedis = redisContext.getConnection();
        try {
            jedis.hset(key, "imageId", imageId);
            jedis.hset(key, "smallId", smallId);
            jedis.hset(key, "mediumId", mediumId);
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

        String[] fields = new String[]{"emails", "name", "rating", "imageId", "smallId", "mediumId", "accountType"};

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

        if (isEmpty(values)) {
            return null;
        } else {
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
                result.setSmallId(values.get(4));
            }
            if (values.get(5) != null) {
                result.setMediumId(values.get(5));
            }
            if (values.get(6) != null) {
                result.setAccountType(AccountEnum.valueOf(values.get(6)));
            }

            return result;
        }
    }

    private boolean isEmpty(List<String> values) {
        if (values != null) {
            for (String value : values) {
                if (!StringUtils.isEmpty(value)) {
                    return false;
                }
            }
        }
        return true;
    }

}


