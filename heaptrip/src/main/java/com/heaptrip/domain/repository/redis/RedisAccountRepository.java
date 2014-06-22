package com.heaptrip.domain.repository.redis;

import com.heaptrip.domain.repository.redis.entity.RedisAccount;

public interface RedisAccountRepository {

    public void save(RedisAccount account);

    public void updateRating(String accountId, double ratingValue);

    public void updateName(String accountId, String name);

    public void updateImages(String accountId, String imageId, String smallId, String mediumId);

    public void remove(String accountId);

    public boolean exists(String accountId);

    public RedisAccount findOne(String accountId);
}
