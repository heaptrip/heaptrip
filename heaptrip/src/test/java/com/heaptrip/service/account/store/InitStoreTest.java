package com.heaptrip.service.account.store;

import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitStoreTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();

        deleteAccounts();

        Locale locale = new Locale("ru");

        userService.registration(UserDataProvider.getEmailUser(), null, locale);
        userRepository.save(UserDataProvider.getActiveUser());
    }

    @AfterTest
    public void afterTest() {
        deleteAccounts();
    }

    public void deleteAccounts() {
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.ACTIVE_USER_ID);
    }
}
