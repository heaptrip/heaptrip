package com.heaptrip.repository.account;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.*;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl extends CrudRepositoryImpl<Account> implements AccountRepository {

    protected static final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);


    @Override
    public Account findById(String accountId) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        return coll.findOne(query, accountId).as(Account.class);
    }

    @Override
    public void changeStatus(String accountId, AccountStatusEnum accountStatus) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String updateQuery = "{$set: {'status': #}}";
        WriteResult wr = coll.update(query, accountId).with(updateQuery, accountStatus.toString());
        logger.debug("WriteResult for update account: {}", wr);
    }

    @Override
    public void changeEmail(String accountId, String newEmail) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String updateQuery = "{$set: {'email': #}}";
        WriteResult wr = coll.update(query, accountId).with(updateQuery, newEmail);
        logger.debug("WriteResult for update account: {}", wr);
    }

    @Override
    public void saveSetting(String accountId, Setting setting) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String updateQuery = "{$set: {'setting': #}}";
        WriteResult wr = coll.update(query, accountId).with(updateQuery, setting);
        logger.debug("WriteResult for update account: {}", wr);
    }

    @Override
    public void saveProfile(String accountId, Profile profile) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String updateQuery = "{$set: {'profile': #}}";
        WriteResult wr = coll.update(query, accountId).with(updateQuery, profile);
        logger.debug("WriteResult for update account: {}", wr);
    }

    @Override
    public List<Account> findUsersByEmail(String email, AccountStatusEnum accountStatus) {
        MongoCollection coll = getCollection();
        String query = "{email: #, typeAccount: #, status: #}";
        Iterable<Account> iterator = coll.find(query, email, AccountEnum.USER, accountStatus.toString()).as(Account.class);
        return IteratorConverter.copyIterator(iterator.iterator());
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.ACCOUNTS.getName();
    }

    @Override
    protected Class<Account> getCollectionClass() {
        return Account.class;
    }

    @Override
    public AccountRating getRating(String accountId) {
        MongoCollection coll = getCollection();
        Account account = coll.findOne("{_id: #}", accountId).projection("{_class: 1, rating: 1}")
                .as(getCollectionClass());
        return (account == null) ? null : account.getRating();
    }

    @Override
    public void updateRating(String accountId, double ratingValue) {
        MongoCollection coll = getCollection();
        coll.update("{_id: #}", accountId).with("{$set: {rating.value: #}, $inc: {'rating.count': 1}}", ratingValue);
    }

    @Override
    public Image getImage(String accountId) {
        MongoCollection coll = getCollection();
        Account account = coll.findOne("{_id: #}", accountId).projection("{_class: 1, image: 1}")
                .as(getCollectionClass());
        return (account == null) ? null : account.getImage();
    }

    @Override
    public void changeImage(String accountId, Image image) {
        MongoCollection coll = getCollection();
        coll.update("{_id: #}", accountId).with("{$set: {image: #}})", image);
    }

    @Override
    public String getName(String accountId) {
        MongoCollection coll = getCollection();
        Account account = coll.findOne("{_id: #}", accountId).projection("{_class: 1, name: 1}")
                .as(getCollectionClass());
        return (account == null) ? null : account.getName();
    }
}
