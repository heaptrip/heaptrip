package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.solr.SolrAccountRepository;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.service.account.user.UserDataProvider;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.heaptrip.domain.service.account.community.CommunityService;

import java.io.IOException;
import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class InitCommunityTest  extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private SolrAccountRepository solrAccountRepository;

    @BeforeTest()
    public void init() throws Exception {
        this.springTestContextPrepareTestInstance();
        deleteAccounts();
        deleteRelation();
    }

    @AfterTest
    public void afterTest() {
        deleteAccounts();
        deleteRelation();
    }

    private void deleteAccounts() {
        communityService.hardRemove(CommunityDataProvider.NOT_CONFIRMED_COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.COMMUNITY_ID);
        communityService.hardRemove(CommunityDataProvider.ACTIVE_COMMUNITY_ID);
    }

    private void deleteRelation() {
        List<Relation> relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(CommunityDataProvider.COMMUNITY_ID));

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
}
