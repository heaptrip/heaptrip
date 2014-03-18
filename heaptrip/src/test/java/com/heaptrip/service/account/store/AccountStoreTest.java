package com.heaptrip.service.account.store;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.user.UserProfile;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AccountStoreTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @Autowired
    private RedisAccountRepository redisAccountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RelationService relationService;

    @Test(enabled = true, priority = 1)
    public void saveAccount() throws SolrServerException, IOException, ExecutionException, InterruptedException {
        Future<Void> future = userService.confirmRegistration(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()));
        future.get();

        solrAccountRepository.commit();

        RedisAccount redisAccount = redisAccountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(redisAccount);

        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(UserDataProvider.EMAIL_USER_NAME);
        criteria.setAccountType(AccountEnum.USER);
        criteria.setSkip(0L);
        criteria.setLimit(1L);

        SolrAccountSearchReponse response = solrAccountRepository.findByAccountSearchCriteria(criteria);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getNumFound() > 0);
        Assert.assertNotNull(response.getAccountIds());
        Assert.assertTrue(response.getAccountIds().length > 0);

        for (String accountId : response.getAccountIds()) {
            Assert.assertTrue(StringUtils.isNotBlank(accountId));
        }
    }

//    @Test(enabled = true, priority = 2)
//    public void acceptFrendship() {
//        relationService.addPublisher(UserDataProvider.EMAIL_USER_ID, UserDataProvider.ACTIVE_USER_ID);
//    }

    @Test(enabled = true, priority = 2)
    public void updateAccount() throws SolrServerException, IOException, ExecutionException, InterruptedException {
        UserProfile profile = new UserProfile();

        String id = "1";
        SimpleCategory[] simpleCategorys = new SimpleCategory[1];
        simpleCategorys[0] = categoryRepository.findOne(id);
        profile.setCategories(simpleCategorys);

        Future<Void> future = userService.saveProfile(UserDataProvider.EMAIL_USER_ID, profile);
        future.get();

        solrAccountRepository.commit();

        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(UserDataProvider.EMAIL_USER_NAME);
        criteria.setAccountType(AccountEnum.USER);
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, new String[] {id}));
        criteria.setSkip(0L);
        criteria.setLimit(1L);

        SolrAccountSearchReponse response = solrAccountRepository.findByAccountSearchCriteria(criteria);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getNumFound() > 0);
        Assert.assertNotNull(response.getAccountIds());
        Assert.assertTrue(response.getAccountIds().length > 0);

        for (String accountId : response.getAccountIds()) {
            Assert.assertTrue(StringUtils.isNotBlank(accountId));
        }
    }

    @Test(enabled = true, priority = 3)
    public void updateImages() {
        // TODO dikma отсутствует реализация изменения картинок у аккаунта!!!
    }

    @Test(enabled = true, priority = 4)
    public void updateRating() throws IOException, ExecutionException, InterruptedException {
        double rating = 10d;
        Future<Void> future = userService.updateAccountRatingValue(UserDataProvider.EMAIL_USER_ID, rating);

        future.get();

        RedisAccount redisAccount = redisAccountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(redisAccount);
        Assert.assertTrue(redisAccount.getRating() == rating);
    }

    @Test(enabled = true, priority = 5)
    public void findActiveUser() {
        Account account = accountStoreService.findOne(UserDataProvider.ACTIVE_USER_ID);
        Assert.assertNotNull(account);
    }

    @Test(enabled = true, priority = 6)
    public void findEmailUser() {
        Account account = accountStoreService.findOne(UserDataProvider.EMAIL_USER_EMAIL);
        Assert.assertNotNull(account);
    }

    @Test(enabled = true, priority = 7)
    public void findByCriteria() throws SolrServerException {
        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(UserDataProvider.EMAIL_USER_NAME);
        criteria.setSkip(0L);
        criteria.setLimit(1L);

        List<Account> accounts = accountStoreService.findByCriteria(criteria);
        Assert.assertNotNull(accounts);
        Assert.assertTrue(accounts.size() == 1);
        Assert.assertTrue(accounts.get(0).getId().equals(UserDataProvider.EMAIL_USER_ID));
    }

    @Test(enabled = true, priority = 10)
    public void removeEmailUser() throws SolrServerException, ExecutionException, InterruptedException, IOException {
        Future<Void> future = accountStoreService.remove(UserDataProvider.EMAIL_USER_ID);
        future.get();

        solrAccountRepository.commit();

        Assert.assertFalse(solrAccountRepository.exists(UserDataProvider.EMAIL_USER_ID));
    }
}
