package com.heaptrip.service.account.community;

import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.account.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class CommunityRegistrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommunityService communityService;

    // регистрация клуба
    @Test(enabled = true, priority = 1)
    public void registrationСlub() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getClub(), locale);
    }

    // регистрация "3 акробатов"
    @Test(enabled = true, priority = 11)
    public void registrationNotConfirmedClub() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getNotConfirmedClub(), locale);
    }

    @Test(enabled = true, priority = 12)
    public void registrationActiveClub() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getActiveClub(), locale);
    }

    @Test(enabled = true, priority = 13)
    public void registrationDeletedClub() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getDeletedClub(), locale);
    }

    // повторная регистрация через email "3 акробатов"
    @Test(enabled = true, priority = 21)
    public void repeatRegistrationNotConfirmedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getNotConfirmedClub(), locale);
    }

    @Test(enabled = true, priority = 22, expectedExceptions = AccountException.class)
    public void repeatRegistrationActiveUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getActiveClub(), locale);
    }

    @Test(enabled = true, priority = 23)
    public void repeatRegistrationDeletedUser() throws NoSuchAlgorithmException, MessagingException, IOException {
        Locale locale = new Locale("ru");
        communityService.registration(CommunityDataProvider.getDeletedClub(), locale);
    }

    // подтверждаем регистрацию клуба
    @Test(enabled = true, priority = 31)
    public void confirmRegistrationClub() {
        communityService.confirmRegistration(CommunityDataProvider.COMMUNITY_ID, String.valueOf(CommunityDataProvider.COMMUNITY_ID.hashCode()));
    }

    // проверяем "левые" вызовы
    @Test(enabled = true, priority = 33, expectedExceptions = AccountException.class)
    public void confirmRegistrationFakeUser() {
        communityService.confirmRegistration(CommunityDataProvider.FAKE_COMMUNITY_ID, String.valueOf(CommunityDataProvider.FAKE_COMMUNITY_ID.hashCode()));
    }

    @Test(enabled = true, priority = 34, expectedExceptions = AccountException.class)
    public void confirmRegistrationActiveUser() {
        communityService.confirmRegistration(CommunityDataProvider.ACTIVE_COMMUNITY_ID, String.valueOf(CommunityDataProvider.ACTIVE_COMMUNITY_ID.hashCode()));
    }

    @Test(enabled = true, priority = 35, expectedExceptions = AccountException.class)
    public void confirmRegistrationDeletedUser() {
        communityService.confirmRegistration(CommunityDataProvider.DELETED_COMMUNITY_ID, String.valueOf(CommunityDataProvider.DELETED_COMMUNITY_ID.hashCode()));
    }

    @Test(enabled = true, priority = 36, expectedExceptions = AccountException.class)
    public void confirmRegistrationWrongHash() {
        communityService.confirmRegistration(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID, "12345");
    }
}
