package com.heaptrip.repository.solr;

import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.repository.solr.entity.SolrAccount;
import com.heaptrip.domain.repository.solr.entity.SolrAccountSearchReponse;
import com.heaptrip.domain.service.account.criteria.AccountTextCriteria;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class SolrAccountRepositoryTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @Test(priority = 1, enabled = true, dataProvider = "solrAccount", dataProviderClass = AccountDataProvider.class)
    public void save(SolrAccount account) throws SolrServerException, IOException {
        // call
        solrAccountRepository.save(account);
        // call only in test method
        solrAccountRepository.commit();
        // check
        Assert.assertTrue(solrAccountRepository.exists(account.getId()));
    }

    @Test(priority = 2, enabled = true, dataProvider = "accountSearchCriteria", dataProviderClass = AccountDataProvider.class)
    public void findByAccountSearchCriteria(AccountTextCriteria criteria) throws SolrServerException {
        // call
        SolrAccountSearchReponse response = solrAccountRepository.findByAccountSearchCriteria(criteria);
        // check
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getNumFound() > 0);
        Assert.assertNotNull(response.getAccountIds());
        Assert.assertTrue(response.getAccountIds().length > 0);
        for (String accountId : response.getAccountIds()) {
            Assert.assertTrue(StringUtils.isNotBlank(accountId));
        }

    }

    @Test(priority = 3, enabled = true, dataProvider = "solrAccount", dataProviderClass = AccountDataProvider.class)
    public void remove(SolrAccount account) throws SolrServerException, IOException {
        // call
        solrAccountRepository.remove(account.getId());
        // call only in test method
        solrAccountRepository.commit();
        // check
        Assert.assertFalse(solrAccountRepository.exists(account.getId()));
    }
}
