package com.heaptrip.service.account.user;

import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.service.account.user.UserService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitUserTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserService userService;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

	@BeforeTest()
	public void init() throws Exception {
		this.springTestContextPrepareTestInstance();
        deleteAccounts();
	}	
	
	@AfterTest
	public void afterTest() {
        deleteAccounts();
	}

    private void deleteAccounts() {
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.NET_USER_ID);
        userService.hardRemove(UserDataProvider.NOT_CONFIRMED_USER_ID);
        userService.hardRemove(UserDataProvider.ACTIVE_USER_ID);

        try {
            solrAccountRepository.remove(UserDataProvider.EMAIL_USER_ID);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            solrAccountRepository.remove(UserDataProvider.NET_USER_ID);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            solrAccountRepository.remove(UserDataProvider.ACTIVE_USER_ID);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
