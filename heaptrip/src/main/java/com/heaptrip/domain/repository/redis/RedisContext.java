package com.heaptrip.domain.repository.redis;

import redis.clients.jedis.Jedis;

public interface RedisContext {

    public Jedis getJedis();
}
