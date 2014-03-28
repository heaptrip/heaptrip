package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.security.Authenticate;
import com.heaptrip.security.AuthenticationListener;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import javax.management.relation.RelationService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@Listeners(AuthenticationListener.class)
public class CommunityRegistrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private RelationRepository relationRepository;

    // регистрация клуба
    @Test(enabled = true, priority = 1)
    @Authenticate(userid = "email", username = "Email User", roles = "ROLE_USER")
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

        List<Relation> relations = relationRepository.findByCriteria(new RelationCriteria(UserDataProvider.EMAIL_USER_ID,
                CommunityDataProvider.COMMUNITY_ID,
                TypeRelationEnum.OWNER));

        Assert.assertTrue(relations.size() == 1);
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
