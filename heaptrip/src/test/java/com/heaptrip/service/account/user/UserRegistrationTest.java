package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.user.UserRegistration;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class UserRegistrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private UserService userService;

    // регистрация 2х базовых пользователей
    @Test(enabled = true, priority = 1)
    public void registrationEmailUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getEmailUser(), null, locale);
    }

    @Test(enabled = true, priority = 2)
    public void registrationNetUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");

        UserRegistration netUser = UserDataProvider.getNetUser();

        Resource resource = loader.getResource(UserDataProvider.IMAGE_1);
        Assert.assertNotNull(resource);
        File file = resource.getFile();
        InputStream is = new FileInputStream(file);

        userService.registration(netUser, is, locale);
    }

    // регистрация "3 акробатов"
    @Test(enabled = true, priority = 11)
    public void registrationNotConfirmedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getNotConfirmedUser(), null, locale);
    }

    @Test(enabled = true, priority = 12)
    public void registrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getActiveUser(), null, locale);
    }

    @Test(enabled = true, priority = 13)
    public void registrationDeletedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getDeletedUser(), null, locale);
    }

    // повторная регистрация через email "3 акробатов"
    @Test(enabled = true, priority = 21)
    public void repeatRegistrationNotConfirmedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getNotConfirmedUser(), null, locale);
    }

    @Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
    public void repeatRegistrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getActiveUser(), null, locale);
    }

    @Test(enabled = true, priority = 23)
    public void repeatRegistrationDeletedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        userService.registration(UserDataProvider.getDeletedUser(), null, locale);
    }

    // подтверждаем регистрацию 2х базовых пользователей
    @Test(enabled = true, priority = 31)
    public void confirmRegistrationEmailUser() {
        userService.confirmRegistration(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()));
    }

    @Test(enabled = true, priority = 32)
    public void confirmRegistrationNetUser() {
        userService.confirmRegistration(UserDataProvider.NET_USER_ID, String.valueOf(UserDataProvider.NET_USER_ID.hashCode()));
    }

    // проверяем "левые" вызовы
    @Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
    public void confirmRegistrationFakeUser() {
        userService.confirmRegistration(UserDataProvider.FAKE_USER_ID, String.valueOf(UserDataProvider.FAKE_USER_ID.hashCode()));
    }

    @Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
    public void confirmRegistrationActiveUser() {
        userService.confirmRegistration(UserDataProvider.ACTIVE_USER_ID, String.valueOf(UserDataProvider.ACTIVE_USER_ID.hashCode()));
    }

    @Test(enabled = true, priority = 35, expectedExceptions = AccountException.class)
    public void confirmRegistrationDeletedUser() {
        userService.confirmRegistration(UserDataProvider.DELETED_USER_ID, String.valueOf(UserDataProvider.DELETED_USER_ID.hashCode()));
    }

    @Test(enabled = true, priority = 36, expectedExceptions = AccountException.class)
    public void confirmRegistrationWrongHash() {
        userService.confirmRegistration(UserDataProvider.NOT_CONFIRMED_USER_ID, "12345");
    }
}
