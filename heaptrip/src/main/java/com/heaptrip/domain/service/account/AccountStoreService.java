package com.heaptrip.domain.service.account;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;

import java.util.List;
import java.util.concurrent.Future;

public interface AccountStoreService {

    public Future<Void> save(String accountId);

    public Future<Void> update(String accountId);

    public Future<Void> updateRating(String accountId, double ratingValue);

    public Future<Void> changeImage(String accountId, Image image);

    public Future<Void> remove(String accountId);

    public Account findOne(String accountId);

    public List<Account> findByCriteria(AccountTextCriteria criteria);
}
