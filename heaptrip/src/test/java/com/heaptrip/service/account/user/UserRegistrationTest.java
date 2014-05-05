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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        userService.confirmRegistration(user.getId(), user.getSendValue());
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
        userService.confirmRegistration(user.getId(), user.getSendValue());
    }

    @Test(enabled = true, priority = 3)
    public void registrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");

        User user = userService.registration(UserDataProvider.getActiveUser(), UserDataProvider.ACTIVE_USER_PSWD, null, locale);
        userService.confirmRegistration(user.getId(), user.getSendValue());
    }

    @Test(enabled = true, priority = 4)
    public void registrationTestUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        notConfirmedUser = userService.registration(UserDataProvider.getNotConfirmedUser(), UserDataProvider.NOT_CONFIRMED_USER_PSWD, null, locale);
    }

    @Test(enabled = true, priority = 5, expectedExceptions = AccountException.class)
    public void confirmWrongUserId() {
        userService.confirmRegistration(UserDataProvider.FAKE_USER_ID, notConfirmedUser.getSendValue());
    }

    @Test(enabled = true, priority = 6, expectedExceptions = AccountException.class)
    public void confirmWrongSendValue() {
        userService.confirmRegistration(notConfirmedUser.getId(), notConfirmedUser.getId());
    }

//    // регистрация 2х базовых пользователей
//    @Test(enabled = true, priority = 1)
//    public void registrationEmailUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getEmailUser(), UserDataProvider.EMAIL_USER_PSWD, null, locale);
//    }
//
//    @Test(enabled = true, priority = 2)
//    public void registrationNetUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//
//        User netUser = UserDataProvider.getNetUser();
//
//        Resource resource = loader.getResource(UserDataProvider.IMAGE_1);
//        Assert.assertNotNull(resource);
//        File file = resource.getFile();
//        InputStream is = new FileInputStream(file);
//
//        userService.registration(netUser, null, is, locale);
//    }
//
//    // регистрация "3 акробатов"
//    @Test(enabled = true, priority = 11)
//    public void registrationNotConfirmedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getNotConfirmedUser(), null, null, locale);
//    }
//
//    @Test(enabled = true, priority = 12)
//    public void registrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getActiveUser(), null, null, locale);
//    }
//
//    @Test(enabled = true, priority = 13)
//    public void registrationDeletedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getDeletedUser(), null, null, locale);
//    }
//
//    // повторная регистрация через email "3 акробатов"
//    @Test(enabled = true, priority = 21)
//    public void repeatRegistrationNotConfirmedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getNotConfirmedUser(), null, null, locale);
//    }
//
//    @Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
//    public void repeatRegistrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getActiveUser(), null, null, locale);
//    }
//
//    @Test(enabled = true, priority = 23)
//    public void repeatRegistrationDeletedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
//        Locale locale = new Locale("ru");
//        userService.registration(UserDataProvider.getDeletedUser(), null, null, locale);
//    }
//
////    подтверждаем регистрацию 2х базовых пользователей
//    @Test(enabled = true, priority = 31)
//    public void confirmRegistrationEmailUser() {
//        userService.confirmRegistration(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()));
//    }
//
//    @Test(enabled = true, priority = 32)
//    public void confirmRegistrationNetUser() {
//        userService.confirmRegistration(UserDataProvider.NET_USER_ID, String.valueOf(UserDataProvider.NET_USER_ID.hashCode()));
//    }
//
//    // проверяем "левые" вызовы
//    @Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
//    public void confirmRegistrationFakeUser() {
//        userService.confirmRegistration(UserDataProvider.FAKE_USER_ID, String.valueOf(UserDataProvider.FAKE_USER_ID.hashCode()));
//    }
//
//    @Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
//    public void confirmRegistrationActiveUser() {
//        userService.confirmRegistration(UserDataProvider.ACTIVE_USER_ID, String.valueOf(UserDataProvider.ACTIVE_USER_ID.hashCode()));
//    }
//
//    @Test(enabled = true, priority = 35, expectedExceptions = AccountException.class)
//    public void confirmRegistrationDeletedUser() {
//        userService.confirmRegistration(UserDataProvider.DELETED_USER_ID, String.valueOf(UserDataProvider.DELETED_USER_ID.hashCode()));
//    }
//
//    @Test(enabled = true, priority = 36, expectedExceptions = AccountException.class)
//    public void confirmRegistrationWrongHash() {
//        userService.confirmRegistration(UserDataProvider.NOT_CONFIRMED_USER_ID, "12345");
//    }
}
