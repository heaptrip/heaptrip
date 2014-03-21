package com.heaptrip.repository.redis;

import com.heaptrip.domain.repository.redis.RedisContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

@Service
public class RedisContextImpl implements RedisContext {

    private static final Logger logger = LoggerFactory.getLogger(RedisContextImpl.class);

    @Value("${redis.url}")
    private String url;

    @Value("${redis.pool.maxActive:20}")
    private int maxActive;

    @Value("${redis.pool.maxIdle:10}")
    private int maxIdle;

    @Value("${redis.pool.minIdle:5}")
    private int minIdle;

    @Value("${redis.pool.maxWait:5000}")
    private int maxWait;

    private JedisPool pool;

    @PostConstruct
    public void init() throws MalformedURLException {
        logger.info("Redis pool connections initialization ...");
        logger.info("redis url: {}", url);
        logger.info("redis.pool.maxActive: {}", maxActive);
        logger.info("redis.pool.maxIdle: {}", maxIdle);
        logger.info("redis.pool.minIdle: {}", minIdle);
        logger.info("redis.pool.maxWait: {}", maxWait);

        if (url == null || url.isEmpty()) {
            throw new RuntimeException("Redis pool connections not initialized: host is not defined");
        }


        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxActive(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWait(maxWait);

        pool = new JedisPool(new JedisPoolConfig(), url);

        logger.info("Redis pool connections successfully initialized");
    }

    @Override
    public Jedis getConnection() {
        return pool.getResource();
    }

    @Override
    public void returnConnection(Jedis jedis) {
        pool.returnResource(jedis);
    }

    @Override
    public void returnBrokenConnection(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }
}
