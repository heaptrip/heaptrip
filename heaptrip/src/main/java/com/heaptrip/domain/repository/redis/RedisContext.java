package com.heaptrip.domain.repository.redis;

import redis.clients.jedis.Jedis;

public interface RedisContext {

    public Jedis getConnection();

    public void returnConnection(Jedis jedis);

    public void returnBrokenConnection(Jedis jedis);
}
