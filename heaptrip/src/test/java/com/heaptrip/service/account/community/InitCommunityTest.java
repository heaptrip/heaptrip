package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.service.account.user.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.entity.account.community.club.Club;
import com.heaptrip.domain.service.account.community.CommunityService;

import java.util.List;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitCommunityTest  extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private RelationRepository relationRepository;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();
        deleteAccounts();
    }

    @AfterTest
    public void afterTest() {
        deleteAccounts();
        deleteRelations();
    }

    private void deleteAccounts() {
        communityService.hardRemove(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.ACTIVE_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.DELETED_COMMUNITY_ID);
    }

    private void deleteRelations() {
        RelationCriteria relationCriteria = new RelationCriteria();
        relationCriteria.setFromId(UserDataProvider.EMAIL_USER_ID);

        List<Relation> relations = relationRepository.findByCriteria(relationCriteria);

        for (Relation relation : relations) {
            relationRepository.remove(relation.getId());
        }
    }
}
