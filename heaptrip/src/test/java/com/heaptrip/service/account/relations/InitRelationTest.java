package com.heaptrip.service.account.relations;

import com.heaptrip.domain.entity.account.notification.Notification;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.notification.NotificationRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.service.account.community.CommunityService;
import com.heaptrip.domain.service.account.criteria.NotificationCriteria;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.service.account.community.CommunityDataProvider;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.List;
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

    @Autowired
    private RelationService relationService;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();

        deleteAll();

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
    public void deleteAll() {
        deleteNotifications();
        deleteRelations();
        deleteAccounts();
    }

    private void deleteNotifications() {
        NotificationCriteria criteria = new NotificationCriteria();
        criteria.setToId(UserDataProvider.EMAIL_USER_ID);
        List<Notification> list = notificationService.getNotifications(criteria);

        criteria.setToId(UserDataProvider.NET_USER_ID);
        list.addAll(notificationService.getNotifications(criteria));

        criteria.setToId(CommunityDataProvider.COMMUNITY_ID);
        list.addAll(notificationService.getNotifications(criteria));

        for (Notification notification : list) {
            notificationRepository.remove(notification.getId());
        }
    }

    private void deleteRelations() {
        RelationCriteria relationCriteria = new RelationCriteria();
        relationCriteria.setFromId(UserDataProvider.EMAIL_USER_ID);

        List<Relation> relations = relationRepository.findByCriteria(relationCriteria);

        for (Relation relation : relations) {
            relationRepository.remove(relation.getId());
        }


        for (Relation relation : relations) {
            try {
                solrAccountRepository.remove(relation.getFromId());
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Relation relation : relations) {
            try {
                solrAccountRepository.remove(relation.getToId());
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteAccounts() {
        userService.hardRemove(UserDataProvider.EMAIL_USER_ID);
        userService.hardRemove(UserDataProvider.NET_USER_ID);
        userService.hardRemove(UserDataProvider.NOT_CONFIRMED_USER_ID);
        userService.hardRemove(UserDataProvider.DELETED_USER_ID);

        communityService.hardRemove(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.DELETED_COMMUNITY_ID);
    }
}
