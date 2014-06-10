package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.account.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.Future;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserRegistrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private UserService userService;

    private User notConfirmedUser;

    @Test(enabled = true, priority = 1)
    public void registrationEmailUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");

        User user = userService.registration(UserDataProvider.getEmailUser(), UserDataProvider.EMAIL_USER_PSWD, null, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));
    }

    @Test(enabled = true, priority = 2)
    public void registrationNetUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");

        User netUser = UserDataProvider.getNetUser();

        Resource resource = loader.getResource(UserDataProvider.IMAGE_1);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);

        User user = userService.registration(netUser, null, is, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));
    }

    @Test(enabled = true, priority = 3)
    public void registrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");

        User user = userService.registration(UserDataProvider.getActiveUser(), UserDataProvider.ACTIVE_USER_PSWD, null, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));
    }

    @Test(enabled = true, priority = 4)
    public void registrationTestUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        notConfirmedUser = userService.registration(UserDataProvider.getNotConfirmedUser(), UserDataProvider.NOT_CONFIRMED_USER_PSWD, null, locale);
    }

    @Test(enabled = true, priority = 5, expectedExceptions = AccountException.class)
    public void confirmWrongUserId() throws UnsupportedEncodingException {
        userService.confirmRegistration(URLEncoder.encode(UserDataProvider.FAKE_USER_ID, "UTF-8"), URLEncoder.encode(notConfirmedUser.getSendValue(), "UTF-8"));
    }

    @Test(enabled = true, priority = 6, expectedExceptions = AccountException.class)
    public void confirmWrongSendValue() throws UnsupportedEncodingException {
        userService.confirmRegistration(URLEncoder.encode(notConfirmedUser.getId(), "UTF-8"), URLEncoder.encode(notConfirmedUser.getId(), "UTF-8"));
    }
}
