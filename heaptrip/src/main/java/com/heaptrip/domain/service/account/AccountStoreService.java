package com.heaptrip.domain.service.account;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;

import java.util.List;

public interface AccountStoreService {

    public void save(String accountId);

    public void update(String accountId);

    public void updateRating(String accountId, double ratingValue);

    public void updateImages(String accountId, String imageId, String thumbnailId);

    public void remove(String accountId);

    public Account findOne(String accountId);

    public List<Account> findByCriteria(AccountTextCriteria criteria);
}
