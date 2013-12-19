package com.heaptrip.domain.repository.redis;

import com.heaptrip.domain.repository.redis.entity.RedisAccount;

public interface RedisAccountRepository {

    public void save(RedisAccount account);

    public void updateRating(String accountId, double ratingValue);

    public void remove(String accountId);

    public boolean exists(String accountId);

    public RedisAccount findOne(String accountId);
}
