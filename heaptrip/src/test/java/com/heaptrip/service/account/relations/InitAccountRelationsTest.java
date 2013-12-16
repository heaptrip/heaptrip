package com.heaptrip.service.account.relations;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.user.UserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitAccountRelationsTest extends AbstractTestNGSpringContextTests {

    public static String USER_IVANOV_ID = "ivanov";
    public static String USER_IVANOV_EMAIL = "support@heaptrip.com";

    public static String USER_PETROV_ID = "petrov";
    public static String USER_PETROV_EMAIL = "support@heaptrip.com";

    public static String USER_SIDOROV_ID = "sidorov";
    public static String USER_SIDOROV_EMAIL = "support@heaptrip.com";

    public static String NOTCONFIRMED_USER_ID = "notConfirmedUser";
    public static String NOTCONFIRMED_USER_EMAIL = "notConfirmedUser@example.com";

    public static String FAKE_USER_ID = "fakeUser";
    public static String FAKE_USER_EMAIL = "fakeUser@example.com";

    public static String COMMUNITY_ID = "club number one";
    public static String COMMUNITY_EMAIL = "support@heaptrip.com";

    public static String NOTCONFIRMED_COMMUNITY_ID = "notConfirmedClub";
    public static String NOTCONFIRMED_COMMUNITY_EMAIL = "notConfirmedClub@example.com";

    public static String FAKE_COMMUNITY_ID = "fakeClub";
    public static String FAKE_COMMUNITY_EMAIL = "fakeClub@example.com";

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();

        Locale locale = new Locale("ru");

        UserRegistration user = new UserRegistration();
        user.setId(USER_IVANOV_ID);
        user.setName("Ivan Ivanov");
        user.setPassword("password1");
        user.setEmail(USER_IVANOV_EMAIL);
        userService.registration(user, null, locale);
        userService.confirmRegistration(USER_IVANOV_ID, String.valueOf(USER_IVANOV_ID.hashCode()));

        user = new UserRegistration();
        user.setId(USER_PETROV_ID);
        user.setName("Petr Petrov");
        user.setPassword("password2");
        user.setEmail(USER_PETROV_EMAIL);
        userService.registration(user, null, locale);
        userService.confirmRegistration(USER_PETROV_ID, String.valueOf(USER_PETROV_ID.hashCode()));

        user = new UserRegistration();
        user.setId(USER_SIDOROV_ID);
        user.setName("Sidr Sidorov");
        user.setPassword("password3");
        user.setEmail(USER_SIDOROV_EMAIL);
        userService.registration(user, null, locale);
        userService.confirmRegistration(USER_SIDOROV_ID, String.valueOf(USER_SIDOROV_ID.hashCode()));

        user = new UserRegistration();
        user.setId(NOTCONFIRMED_USER_ID);
        user.setName("Igor Igorev");
        user.setPassword("password4");
        user.setEmail(NOTCONFIRMED_USER_EMAIL);
        userService.registration(user, null, locale);

        Club club = new Club();
        club.setId(COMMUNITY_ID);
        club.setName("Club number one");
        club.setEmail(COMMUNITY_EMAIL);
        communityService.registration(club, locale);
        communityService.confirmRegistration(COMMUNITY_ID, String.valueOf(COMMUNITY_ID.hashCode()));

        club = new Club();
        club.setId(NOTCONFIRMED_COMMUNITY_ID);
        club.setName("not Confirmed Club");
        club.setEmail(NOTCONFIRMED_COMMUNITY_EMAIL);
        communityService.registration(club, locale);
    }

    @AfterTest
    public void afterTest() {
        userService.hardRemove(NOTCONFIRMED_USER_ID);
        userService.hardRemove(USER_SIDOROV_ID);
        userService.hardRemove(USER_PETROV_ID);
        userService.hardRemove(USER_IVANOV_ID);

        communityService.hardRemove(NOTCONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(COMMUNITY_ID);
    }
}
