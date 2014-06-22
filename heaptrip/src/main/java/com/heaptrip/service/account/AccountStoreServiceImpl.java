package com.heaptrip.service.account;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.community.agency.Agency;
import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.entity.account.community.company.Company;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.FileReferences;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.SystemException;
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
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.image.ImageService;
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
    private ImageService imageService;

    @Autowired
    private RelationRepository relationRepository;

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
            solrAccount.setClazz(account.getAccountType().getClazz());
            if (account.getProfile() != null) {
                solrAccount.setCategories(getIds(account.getProfile().getCategories()));
                solrAccount.setRegions(getIds(account.getProfile().getRegions()));
            }

            if (!account.getAccountType().equals(AccountEnum.USER)) {
                String[] types = new String[1];
                types[0] = RelationTypeEnum.OWNER.toString();

                AccountRelationCriteria criteria = new AccountRelationCriteria(accountId, types);
                List<Relation> relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setOwners(relations.get(0).getUserIds());
                }
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
            redisAccount.setAccountType(account.getAccountType());
            if (account.getRating() != null) {
                redisAccount.setRating(account.getRating().getValue());
            }
            if (account.getImage() != null) {
                redisAccount.setImageId(account.getImage().getId());
                if (account.getImage().getRefs() != null) {
                    redisAccount.setSmallId(account.getImage().getRefs().getSmall());
                    redisAccount.setMediumId(account.getImage().getRefs().getMedium());
                }
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
            solrAccount.setClazz(account.getAccountType().getClazz());
            if (account.getProfile() != null) {
                solrAccount.setCategories(getIds(account.getProfile().getCategories()));
                solrAccount.setRegions(getIds(account.getProfile().getRegions()));
            }

            AccountRelationCriteria criteria = new AccountRelationCriteria(accountId);
            String[] types = new String[1];
            List<Relation> relations;

            if (account.getAccountType().equals(AccountEnum.USER)) {
                types[0] = RelationTypeEnum.PUBLISHER.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setPublishers(relations.get(0).getUserIds());
                }

                types[0] = RelationTypeEnum.FRIEND.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setFriends(relations.get(0).getUserIds());
                }
            } else {
                types[0] = RelationTypeEnum.SUBSCRIBER.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setPublishers(relations.get(0).getUserIds());
                }

                types[0] = RelationTypeEnum.MEMBER.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setMembers(relations.get(0).getUserIds());
                }

                types[0] = RelationTypeEnum.EMPLOYEE.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setStaff(relations.get(0).getUserIds());
                }

                types[0] = RelationTypeEnum.OWNER.toString();
                criteria.setRelationTypes(types);
                relations = relationRepository.findByAccountRelationCriteria(criteria);

                if (relations != null && relations.size() == 1 && relations.get(0).getUserIds() != null && relations.get(0).getUserIds().length > 0) {
                    solrAccount.setOwners(relations.get(0).getUserIds());
                }
            }

            try {
                solrAccountRepository.save(solrAccount);
            } catch (SolrServerException | IOException e) {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                throw errorService.createException(SolrException.class, e, ErrorEnum.ERR_SYSTEM_SOLR);
            }

            try {
                // TODO: dikma нужно иметь возможность залогировать исключение не выходя из метода
                redisAccountRepository.updateName(accountId, account.getName());
            } catch (Exception e) {
                throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
            }
        }

        return new AsyncResult<>(null);
    }

    @Override
    public void updateRating(String accountId, double ratingValue) {
        Assert.notNull(accountId, "accountId must not be null");
        try {
            redisAccountRepository.updateRating(accountId, ratingValue);
        } catch (Exception e) {
            throw errorService.createException(RedisException.class, e, ErrorEnum.ERR_SYSTEM_REDIS);
        }
    }

    @Async
    @Override
    public Future<Void> changeImage(String accountId, Image image) {
        Assert.notNull(accountId, "accountId must not be null");
        if (image == null) {
            //TODO konovalov: set default image
        } else {
            Assert.notNull(image.getId(), "image.id must not be null");
            Image oldImage = accountRepository.getImage(accountId);
            if (oldImage != null) {
                imageService.removeImageById(oldImage.getId());
            }
            accountRepository.changeImage(accountId, image);
            String smallId = (image.getRefs() == null) ? null : image.getRefs().getSmall();
            String mediumId = (image.getRefs() == null) ? null : image.getRefs().getMedium();
            redisAccountRepository.updateImages(accountId, image.getId(), smallId, mediumId);
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

        RedisAccount redisAccount = redisAccountRepository.findOne(accountId);

        if (redisAccount == null) {
            logger.warn("account not exists in Redis: " + accountId);

            Account account = accountRepository.findOne(accountId);

            if (account == null) {
                return null;
            }

            redisAccount = new RedisAccount();
            redisAccount.setId(account.getId());
            redisAccount.setName(account.getName());
            redisAccount.setEmail(account.getEmail());
            redisAccount.setAccountType(account.getAccountType());
            if (account.getRating() != null) {
                redisAccount.setRating(account.getRating().getValue());
            }
            if (account.getImage() != null) {
                redisAccount.setImageId(account.getImage().getId());
                if (account.getImage().getRefs() != null) {
                    redisAccount.setSmallId(account.getImage().getRefs().getSmall());
                    redisAccount.setMediumId(account.getImage().getRefs().getMedium());
                }
            }

            redisAccountRepository.save(redisAccount);

            return account;
        } else {
            Assert.notNull(redisAccount.getAccountType(), "accountType must not be null");

            Account account;
            switch (redisAccount.getAccountType()) {
                case USER:
                    account = new User();
                    break;
                case AGENCY:
                    account = new Agency();
                    break;
                case CLUB:
                    account = new Club();
                    break;
                case COMPANY:
                    account = new Company();
                    break;
                default:
                    throw new SystemException("Error accountType: " + redisAccount.getAccountType());
            }

            account.setId(redisAccount.getId());
            account.setName(redisAccount.getName());

            AccountRating rating = new AccountRating();
            rating.setValue(redisAccount.getRating());
            account.setRating(rating);

            if (redisAccount.getImageId() != null) {
                Image image = new Image();
                image.setId(redisAccount.getImageId());
                image.setRefs(new FileReferences());
                image.getRefs().setSmall(redisAccount.getSmallId());
                image.getRefs().setMedium(redisAccount.getMediumId());
                account.setImage(image);
            }

            return account;
        }
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

//    private String[] getRelations(RelationCriteria criteria, AccountEnum typeAccount) {
//        List<Relation> relations;
//        String[] result;
//
//        relations = relationRepository.findByCriteria(criteria);
//        result = new String[relations.size()];
//
//        if (relations.size() > 0) {
//            for (int i = 0; relations.size() > i; i++) {
//                if (typeAccount.equals(AccountEnum.USER)) {
//                    result[i] = relations.get(i).getToId();
//                } else {
//                    result[i] = relations.get(i).getFromId();
//                }
//            }
//        }
//
//        return result;
//    }
}
