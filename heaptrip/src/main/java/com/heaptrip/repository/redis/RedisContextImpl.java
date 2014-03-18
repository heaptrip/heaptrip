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

    private JedisPool pool;

    @PostConstruct
    public void init() throws MalformedURLException {
        logger.info("Redis pool connections initialization ...");
        logger.info("redis url: {}", url);

        if (url == null || url.isEmpty()) {
            throw new RuntimeException("Redis pool connections not initialized: host is not defined");
        }


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
