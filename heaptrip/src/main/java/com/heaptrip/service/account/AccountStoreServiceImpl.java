package com.heaptrip.service.account;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountImageReferences;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.exception.system.RedisException;
import com.heaptrip.domain.exception.system.SolrException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.domain.service.system.ErrorService;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class AccountStoreServiceImpl implements AccountStoreService {

    protected static final Logger logger = LoggerFactory.getLogger(AccountStoreServiceImpl.class);

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @Autowired
    private RedisAccountRepository redisAccountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    RelationRepository relationRepository;

    @Async
    @Override
    public Future<Void> save(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        Account account = accountRepository.findOne(accountId);

        if (account == null) {
            String msg = String.format("account not find by id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
        }
        if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_ACTIVE);
        } else {
            SolrAccount solrAccount = new SolrAccount();
            solrAccount.setId(account.getId());
            solrAccount.setName(account.getName());
            solrAccount.setClazz(account.getTypeAccount().getClazz());
            if (account.getProfile() != null) {
                solrAccount.setCategories(getIds(account.getProfile().getCategories()));
                solrAccount.setRegions(getIds(account.getProfile().getRegions()));
            }

            try {
                solrAccountRepository.save(solrAccount);
            } catch (SolrServerException | IOException e) {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
            }

            RedisAccount redisAccount = new RedisAccount();
            redisAccount.setId(account.getId());
            redisAccount.setName(account.getName());
            redisAccount.setEmail(account.getEmail());
            if (account.getRating() != null) {
                redisAccount.setRating(account.getRating().getValue());
            }
            if (account.getImages() != null) {
                redisAccount.setImageId(account.getImages().getProfileId());
                redisAccount.setThumbnailId(account.getImages().getContentId());
            }

            try {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                redisAccountRepository.save(redisAccount);
            } catch (Exception e) {
                throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
            }
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Override
    public Future<Void> update(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        Account account = accountRepository.findOne(accountId);

        if (account == null) {
            String msg = String.format("account not find by id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
        } else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_ACTIVE);
        } else {
            SolrAccount solrAccount = new SolrAccount();
            solrAccount.setId(account.getId());
            solrAccount.setName(account.getName());
            solrAccount.setClazz(account.getTypeAccount().getClazz());
            if (account.getProfile() != null) {
                solrAccount.setCategories(getIds(account.getProfile().getCategories()));
                solrAccount.setRegions(getIds(account.getProfile().getRegions()));
            }

            RelationCriteria criteria = new RelationCriteria();
            criteria.setFromId(accountId);
            criteria.setTypeRelation(TypeRelationEnum.PUBLISHER);
            solrAccount.setPublishers(getRelations(criteria));

            criteria.setTypeRelation(TypeRelationEnum.MEMBER);
            solrAccount.setMembers(getRelations(criteria));

            criteria.setTypeRelation(TypeRelationEnum.EMPLOYEE);
            solrAccount.setStaff(getRelations(criteria));

            criteria.setTypeRelation(TypeRelationEnum.OWNER);
            solrAccount.setOwners(getRelations(criteria));

            criteria.setTypeRelation(TypeRelationEnum.FRIEND);
            solrAccount.setFriends(getRelations(criteria));

            try {
                solrAccountRepository.save(solrAccount);
            } catch (SolrServerException | IOException e) {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
            }
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Override
    public Future<Void> updateRating(String accountId, double ratingValue) {
        Assert.notNull(accountId, "accountId must not be null");
        try {
            redisAccountRepository.updateRating(accountId, ratingValue);
        } catch (Exception e) {
            throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Override
    public Future<Void> updateImages(String accountId, String imageId, String thumbnailId) {
        Assert.notNull(accountId, "accountId must not be null");
        try {
            redisAccountRepository.updateImages(accountId, imageId, thumbnailId);
        } catch (Exception e) {
            throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Override
    public Future<Void> remove(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        try {
            solrAccountRepository.remove(accountId);
        } catch (SolrServerException | IOException e) {
            throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
        }

        return new AsyncResult<>(null);
    }

    @Override
    public Account findOne(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

        RedisAccount redisAccount;
        Account account;

        try {
            redisAccount = redisAccountRepository.findOne(accountId);
        } catch (Exception e) {
            throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
        }

        if (redisAccount == null) {
            String msg = String.format("account not exists in Redis: %s", accountId);
            logger.warn(msg);
            account = accountRepository.findOne(accountId);

            redisAccount = new RedisAccount();
            redisAccount.setId(account.getId());
            redisAccount.setName(account.getName());
            redisAccount.setEmail(account.getEmail());
            if (account.getRating() != null) {
                redisAccount.setRating(account.getRating().getValue());
            }
            if (account.getImages() != null) {
                redisAccount.setImageId(account.getImages().getProfileId());
                redisAccount.setThumbnailId(account.getImages().getContentId());
            }

            try {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                redisAccountRepository.save(redisAccount);
            } catch (Exception e) {
                throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
            }

        } else {
            account = new Account();
            account.setId(redisAccount.getId());
            account.setName(redisAccount.getName());

            AccountRating rating = new AccountRating();
            rating.setValue(redisAccount.getRating());
            account.setRating(rating);

            AccountImageReferences images = new AccountImageReferences();
            images.setProfileId(redisAccount.getImageId());
            images.setContentId(redisAccount.getThumbnailId());
            account.setImages(images);
        }

        return account;
    }

    @Override
    public List<Account> findByCriteria(AccountTextCriteria criteria) {
        List<Account> result = null;
        SolrAccountSearchReponse response;

        try {
            response = solrAccountRepository.findByAccountSearchCriteria(criteria);
        } catch (SolrServerException e) {
            throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
        }

        if (response != null && response.getAccountIds() != null && response.getAccountIds().length > 0) {
            Account account;
            result = new ArrayList<>(response.getAccountIds().length);
            for (String accountId : response.getAccountIds()) {
                account = findOne(accountId);
                if (account != null) {
                    result.add(account);
                }
            }
        }

        return result;
    }

    private String[] getIds(BaseObject[] objects) {
        if (objects == null || objects.length == 0) {
            return null;
        } else {
            String[] ids = new String[objects.length];

            for (int i = 0; objects.length > i; i++) {
                ids[i] = objects[i].getId();
            }

            return ids;
        }
    }

    private String[] getRelations(RelationCriteria criteria) {
        // TODO нет проверки на уникальность связей
        List<Relation> relations;
        String[] result;

        relations = relationRepository.findByCriteria(criteria);
        result = new String[relations.size()];

        if (relations.size() > 0) {
            for (int i = 0; relations.size() > i; i++) {
                result[i] = relations.get(i).getToId();
            }
        }

        return result;
    }
}
