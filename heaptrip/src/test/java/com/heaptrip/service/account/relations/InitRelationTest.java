package com.heaptrip.service.account.relations;

import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.service.account.community.CommunityDataProvider;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitRelationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();

        Locale locale = new Locale("ru");

        userService.registration(UserDataProvider.getEmailUser(), null, locale);
        userService.confirmRegistration(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()));

        userService.registration(UserDataProvider.getNetUser(), null, locale);
        userService.confirmRegistration(UserDataProvider.NET_USER_ID, String.valueOf(UserDataProvider.NET_USER_ID.hashCode()));

        userService.registration(UserDataProvider.getNotConfirmedUser(), null, locale);

        userRepository.save(UserDataProvider.getDeletedUser());


        communityService.registration(CommunityDataProvider.getClub(), locale);
        communityService.confirmRegistration(CommunityDataProvider.COMMUNITY_ID, String.valueOf(CommunityDataProvider.COMMUNITY_ID.hashCode()));

        communityService.registration(CommunityDataProvider.getNotConfirmedClub(), locale);

        accountRepository.save(CommunityDataProvider.getDeletedClub());
    }

    @AfterTest
    public void afterTest() {
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.NET_USER_ID);
        userService.hardRemove(UserDataProvider.NOT_CONFIRMED_USER_ID);
        userService.hardRemove(UserDataProvider.DELETED_USER_ID);

        communityService.hardRemove(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.DELETED_COMMUNITY_ID);
    }
}
