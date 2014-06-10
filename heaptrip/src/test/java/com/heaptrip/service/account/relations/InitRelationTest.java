package com.heaptrip.service.account.relations;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.security.Authenticate;
import com.heaptrip.security.AuthenticationListener;
import com.heaptrip.service.account.community.CommunityDataProvider;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
@Listeners(AuthenticationListener.class)
public class InitRelationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @BeforeTest
    @Authenticate(userid = "email", username = "Email User", roles = "ROLE_USER")
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();

        deleteAll();
    }

    @Test(enabled = true)
    @Authenticate(userid = "email", username = "Email User", roles = "ROLE_USER")
    public void prepare() throws Exception {
        Locale locale = new Locale("ru");

        User user = userService.registration(UserDataProvider.getEmailUser(), UserDataProvider.EMAIL_USER_PSWD, null, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));

        user = userService.registration(UserDataProvider.getNetUser(), null, null, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));

        user = userService.registration(UserDataProvider.getActiveUser(), UserDataProvider.ACTIVE_USER_PSWD, null, locale);
        userService.confirmRegistration(URLEncoder.encode(user.getId(), "UTF-8"), URLEncoder.encode(user.getSendValue(), "UTF-8"));

        userService.registration(UserDataProvider.getNotConfirmedUser(), UserDataProvider.NOT_CONFIRMED_USER_PSWD, null, locale);

        Community community = communityService.registration(CommunityDataProvider.getClub(), locale);
        communityService.confirmRegistration(URLEncoder.encode(community.getId(), "UTF-8"), URLEncoder.encode(community.getSendValue(), "UTF-8"));

        communityService.registration(CommunityDataProvider.getNotConfirmedClub(), locale);
    }

    @AfterTest
    public void deleteAll() {
        deleteNotifications();
        deleteRelations();
        deleteAccounts();
    }

    private void deleteNotifications() {
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
        criteria.setToId(UserDataProvider.NET_USER_ID);
        List<Notification> notifications = notificationService.findByNotificationCriteria(criteria);

        criteria.setFromId(UserDataProvider.NET_USER_ID);
        criteria.setToId(UserDataProvider.EMAIL_USER_ID);
        notifications.addAll(notificationService.findByNotificationCriteria(criteria));

        criteria.setFromId(UserDataProvider.EMAIL_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        notifications.addAll(notificationService.findByNotificationCriteria(criteria));

        criteria.setFromId(UserDataProvider.NET_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        notifications.addAll(notificationService.findByNotificationCriteria(criteria));

        criteria.setFromId(UserDataProvider.ACTIVE_USER_ID);
        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        notifications.addAll(notificationService.findByNotificationCriteria(criteria));

        for (Notification notification : notifications) {
            notificationRepository.remove(notification.getId());
        }
    }

    private void deleteRelations() {
        List<Relation> relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(UserDataProvider.EMAIL_USER_ID));

        if (relations != null) {
            for (Relation relation : relations) {
                relationRepository.remove(relation.getId());

                try {
                    solrAccountRepository.remove(relation.getFromId());
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(UserDataProvider.NET_USER_ID));

        if (relations != null) {
            for (Relation relation : relations) {
                relationRepository.remove(relation.getId());

                try {
                    solrAccountRepository.remove(relation.getFromId());
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(CommunityDataProvider.COMMUNITY_ID));

        if (relations != null) {
            for (Relation relation : relations) {
                relationRepository.remove(relation.getId());

                try {
                    solrAccountRepository.remove(relation.getFromId());
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteAccounts() {
        userService.hardRemove(UserDataProvider.ACTIVE_USER_ID);
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.NET_USER_ID);
        userService.hardRemove(UserDataProvider.NOT_CONFIRMED_USER_ID);

        communityService.hardRemove(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.COMMUNITY_ID);
    }
}
