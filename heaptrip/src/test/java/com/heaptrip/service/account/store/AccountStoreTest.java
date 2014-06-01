package com.heaptrip.service.account.store;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserProfile;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.redis.RedisAccountRepository;
import com.heaptrip.domain.repository.redis.entity.RedisAccount;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.criteria.CheckModeEnum;
import com.heaptrip.domain.service.criteria.IDListCriteria;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RatingService ratingService;


    @Test(enabled = true, priority = 1)
    public void saveAccount() throws SolrServerException, IOException, ExecutionException, InterruptedException, MessagingException, NoSuchAlgorithmException {
        Locale locale = new Locale("ru");

        User user = userService.registration(UserDataProvider.getEmailUser(), UserDataProvider.EMAIL_USER_PSWD, null, locale);
        Future<Void> future = userService.confirmRegistration(user.getId(), user.getSendValue());
        future.get();

        solrAccountRepository.commit();

        RedisAccount redisAccount = redisAccountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(redisAccount);

        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(UserDataProvider.EMAIL_USER_NAME);
        criteria.setAccountType(new IDListCriteria(CheckModeEnum.IN, new String[]{AccountEnum.USER.getClazz()}));
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

    @Test(enabled = true, priority = 2)
    public void updateAccount() throws SolrServerException, IOException, ExecutionException, InterruptedException {
        UserProfile profile = new UserProfile();

        String id = "1";
        SimpleCategory[] simpleCategorys = new SimpleCategory[1];
        simpleCategorys[0] = categoryRepository.findOne(id);
        profile.setCategories(simpleCategorys);

        Future<Void> future = userService.saveProfile(UserDataProvider.EMAIL_USER_ID, UserDataProvider.EMAIL_USER_NAME, profile);
        future.get();

        solrAccountRepository.commit();

        AccountTextCriteria criteria = new AccountTextCriteria();
        criteria.setQuery(UserDataProvider.EMAIL_USER_NAME);
        criteria.setAccountType(new IDListCriteria(CheckModeEnum.IN, new String[]{AccountEnum.USER.getClazz()}));
        criteria.setCategories(new IDListCriteria(CheckModeEnum.IN, new String[]{id}));
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

    @Test(enabled = true, priority = 4)
    public void setAccountRating() {
        // call
        AccountRating accountRating = ratingService.getDefaultAccountRating();
        userService.setAccountRating(UserDataProvider.EMAIL_USER_ID, accountRating);
        // check
        Account account = accountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(account);
        Assert.assertNotNull(account.getRating());
        Assert.assertEquals(account.getRating().getCount(), accountRating.getCount());
        Assert.assertEquals(account.getRating().getValue(), accountRating.getValue());
        Assert.assertEquals(account.getRating().getK(), accountRating.getK());

        RedisAccount redisAccount = redisAccountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(redisAccount);
        Assert.assertTrue(redisAccount.getRating() == accountRating.getValue());
    }

    @Test(enabled = true, priority = 5)
    public void updateRating() throws IOException, ExecutionException, InterruptedException {
        double rating = 10d;
        userService.updateAccountRatingValue(UserDataProvider.EMAIL_USER_ID, rating);

        // check
        Account account = accountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(account);
        Assert.assertNotNull(account.getRating());
        Assert.assertEquals(account.getRating().getValue(), rating);

        RedisAccount redisAccount = redisAccountRepository.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(redisAccount);
        Assert.assertEquals(redisAccount.getRating(), rating);
    }

    @Test(enabled = true, priority = 6)
    public void findActiveUser() {
        Account account = accountStoreService.findOne(UserDataProvider.ACTIVE_USER_ID);
        Assert.assertNotNull(account);
    }

    @Test(enabled = true, priority = 7)
    public void findEmailUser() {
        Account account = accountStoreService.findOne(UserDataProvider.EMAIL_USER_ID);
        Assert.assertNotNull(account);
    }

    @Test(enabled = true, priority = 8)
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

    @Test(enabled = true, priority = 9)
    public void removeEmailUser() throws SolrServerException, ExecutionException, InterruptedException, IOException {
        Future<Void> future = accountStoreService.remove(UserDataProvider.EMAIL_USER_ID);
        future.get();

        solrAccountRepository.commit();

        Assert.assertFalse(solrAccountRepository.exists(UserDataProvider.EMAIL_USER_ID));
    }
}
